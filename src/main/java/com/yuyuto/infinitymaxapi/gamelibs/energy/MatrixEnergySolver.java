package com.yuyuto.infinitymaxapi.gamelibs.energy;

import java.util.HashMap;
import java.util.Map;

/**
 * Solves the resistive network using nodal analysis.
 * Builds conductance matrix G and current vector I.
 * Applies ground constraint (V0 = 0).
 * Solves 'G * V = I' using Gaussian elimination.
 * Assumptions:
 * - Only resistors and current sources are supported.
 * - One node is fixed as ground.
 * ノード解析を用いて抵抗ネットワークを解く。
 * 伝導度行列「G」とベクトル「I」を構築する。
 * 接地制約(V0=0)を適用する。
 * G*V=Iをガウス消去法で解く。
 */

public class MatrixEnergySolver implements EnergySolver {

    @Override
    public void solve(EnergyNetwork network, double deltaTime) {

        int n = network.getNodes().size(); //未知数の電圧(=わからない電圧)がいくつあるかをカウントしてノードに代入する
        double[][] G = new double[n][n];//G行列を生成。各要素はコンダクタンス(1/R)の組み合わせ。
        double[] I = new double[n];//電流ベクトル。各ノードに外部から流れ込む電流。

        Map<EnergyNode, Integer> indexMap = new HashMap<>();//ノードを「行列の番号」に対応付ける辞書
        int i = 0;

        //ノード0,1,2...と番号をつける。行列は番号でしか扱えないため。
        for (EnergyNode node : network.getNodes()) {
            indexMap.put(node, i++);
        }
        for (EnergyNode node : network.getNodes()){
            int idx = indexMap.get(node);
            I[idx] += node.getInjectedCurrent();
        }

        /*
          ここからG行列を構築するよ！
          G行列とは電気ルールの箱の行列だよ！
          抵抗が大きいと電流は流れにくいから1/Rを使うよ！
          ここでいう1/Rは流れやすさを表すよ！
         */
        //各抵抗について、
        for (EnergyConnection C : network.getConnections()) {
            //接続の両端ノードの行番号を取得する
            int i1 = indexMap.get(C.from());
            int i2 = indexMap.get(C.to());
            //抵抗R→コンダクタスG=1/R(ノード解析は1/Rで扱うため)
            double conductance = 1.0 / C.resistance();

            /*
             * ここが主要処理
             * 簡単に言うと、NodeAとNodeBが線で繋がっていたらAとBはお互いに影響しあうという処理。
             * プラスは「自分の重み」
             * マイナスは「相手との関係」
             * 物理的な意味として、(Vi=Vj)/Rという式を展開すると、
             * Vi*(1/R) - Vj*(1/R)
             * という式が出来上がる。なので、
             * 自分の対角成分に+1/R
             * 相手の成分に-1/R
             * が入る。
             * 以下の4文ではそれを行っている。
             *
             *
             */
            G[i1][i1] += conductance;
            G[i2][i2] += conductance;
            G[i1][i2] -= conductance;
            G[i2][i1] -= conductance;
        }

        for (EnergyNode node : network.getNodes()) {
            if (node instanceof ChargedEnergyNode charged){
                int idx = indexMap.get(node);
                double internalR = charged.getInternalResistance();
                double conductance = 1.0 / internalR;

                double voltage = charged.computeVoltageFromCharge();
                //自分自身にコンダクタンスを追加
                G[idx][idx] += conductance;

                //電圧源項をIベクトルへ
                I[idx] +=  voltage / internalR;

            }
        }

        /*
        *ここからGND固定処理部分。
        * GNDはグランドという。グランドとは、電圧の基準を示す。
        * ここでの電圧基準は0としている。
        */
        int gnd = 0;

        //GND行・列を0にする。
        for (int j = 0; j < n; j++) {
            G[gnd][j] = 0;
            G[j][gnd] = 0;
        }

        //方程式をV_gnd = 0に置き換える。これをしないと行列が解けない。
        G[gnd][gnd] = 1;
        I[gnd] = 0;

        double[] V = gaussianElimination(G,I);

        for (EnergyNode node : network.getNodes()){
            int idx = indexMap.get(node);
            //ゴール:計算した電圧をノードに書き出す。電流もノードに書き出す。
            node.setPotential(V[idx]);

            if (node instanceof ChargedEnergyNode charged){
                double totalCurrent = 0;

                for (EnergyConnection conn : network.getConnections()){

                    int iFrom = indexMap.get(conn.from());
                    int iTo = indexMap.get(conn.to());

                    double voltageDiff = V[iFrom] - V[iTo];
                    double currentFlow = voltageDiff /conn.resistance();

                    if (conn.from() == node) {
                        totalCurrent += currentFlow; //外へ出る
                    }

                    if (conn.to() == node) {
                        totalCurrent -= currentFlow;//中に入る
                    }
                }

                double nodeVoltage = V[idx];
                double sourceVoltage = charged.computeVoltageFromCharge();

                double internalCurrent =
                        (nodeVoltage - sourceVoltage) / charged.getInternalResistance();
                totalCurrent += internalCurrent;
                charged.addCharge(-totalCurrent * deltaTime);
            }
        }

        applyTransmissionLoss(network, V, indexMap);
    }

    /*
    *主要メソッド:ガウス消去法
    * ガウス消去法とは、簡単には「連立方程式を順番に簡単にして解いていく方法」のこと。
    * やっていることとしては
    * 1.一番上を1つの式にする
    * 2.下の式から上の式を消す
    * 3.三角形にする
    * 4.後ろから答えを出す
    * というもの。Developerでもわかんない。
    */
    private double[] gaussianElimination(double[][] A, double[] b){

        int n = b.length;//nをb.lengthの数字を初期値として宣言する

        for (int p = 0; p < n; p++) {

            //ピポット選択
            int max = p;//maxをpの数字を初期値として宣言する
            for (int i = p + 1; i < n; i++) { //一番大きい数値をピボットにする
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;//maxをiにする
                }
            }
            //ピボット行を上に持ってくる。入れ替え処理をしている。
            double[] temp = A[p]; //tempをA[p]の数字を代入する
            A[p] = A[max];//A[p]にA[max]の数字を代入する
            A[max] = temp;//A[max]にtempの数字を代入する

            double t = b[p]; //tをb[p]の数字を初期値として宣言する
            b[p] = b[max];//b[p]にb[max]の数字を代入する
            b[max] = t;//b[max]にtの数字を代入する

            //前進消去。下の式から上の式を引いて文字を消す処理。
            for (int i = p + 1; i < n; i++) {
                //倍率計算
                double alpha = A[i][p] /A[p][p];
                b[i] -= alpha * b[p];

                for (int j = p; j < n; j++){
                    //下三角をゼロ化・行列を上三角に変形している。
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        //後退消去
        double[] x = new double[n];

        for (int i = n - 1; i >= 0; i--){
            double sum = b[i];

            for (int j = i + 1; j < n; j++){
                sum -= A[i][j] * x[j];
            }

            //後退代入。一番下の式から答えを出していく。
            x[i] = sum / A[i][i];
        }

        return x;
    }

    private void applyTransmissionLoss(EnergyNetwork network, double[] voltages, Map<EnergyNode, Integer> indexMap){
        for(EnergyConnection conn : network.getConnections()){
            int iFrom = indexMap.get(conn.from());
            int iTo = indexMap.get(conn.to());

            double voltageDiff = voltages[iFrom] - voltages[iTo];
            double currentFlow = voltageDiff / conn.resistance();

            //電力損失　= I^2*R
            double loss = currentFlow * currentFlow * conn.resistance();

            //ノードに減衰を適用
            conn.from().addPowerLoss(loss / 2); //送電側に半分
            conn.to().addPowerLoss(loss / 2);//受電側に半分
        }
    }
}