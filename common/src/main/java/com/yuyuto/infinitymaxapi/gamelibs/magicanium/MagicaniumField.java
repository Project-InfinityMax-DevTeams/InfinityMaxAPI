package com.yuyuto.infinitymaxapi.gamelans.magicanium;

import java.util.ArrayList;
import java.util.List;

/**
 * MagicaniumField
 *
 * 空間上に存在するMagicalBodyを管理し、時間発展（tick）を行う
 * - 正密度の自然崩壊
 * - 負密度の減衰
 * - 空間平均化による拡散
 *
 * dρ⁺/dt = -βρ⁺(1-σ) + ερ⁺² - ζρ⁺³  （非線形暴走項）
 * dρ⁻/dt = -μρ⁻
 */
public class MagicaniumField implements TimeEvolvable {

    /** この場に存在する全魔力体 */
    private final List<MagicalBody> bodies = new ArrayList<>();

    /**
     * 魔力体を場に追加
     * @param body MagicalBody
     */
    public void addBody(MagicalBody body) {
        bodies.add(body);
    }

    /**
     * 時間経過処理
     * @param deltaTime 経過時間（秒など）
     */
    @Override
    public void tick(double deltaTime) {

        // ---- ① 自然崩壊・非線形変化 ----
        for (MagicalBody body : bodies) {

            MagicaniumState state = body.getState();

            double rhoP = state.getRhoPositive();
            double rhoN = state.getRhoNegative();
            double stability = state.getStability();

            // 正密度の非線形変化（自然崩壊 + 暴走項）
            double dRhoP = (-WorldConstants.BETA * rhoP * (1.0 - stability)
                            + WorldConstants.EPSILON * rhoP * rhoP
                            - WorldConstants.ZETA * rhoP * rhoP * rhoP) * deltaTime;

            // 負密度の単純減衰
            double dRhoN = -WorldConstants.MU * rhoN * deltaTime;

            // 変化量を加算
            state.addPositive(dRhoP);
            state.addNegative(dRhoN);
        }

        // ---- ② 空間平均化処理 ----
        equalizeDensity(deltaTime);
    }

    /**
     * 全体正密度をゆるやかに平均化
     * - 各体が平均値に向かう
     * - deltaTimeでスケーリング
     * @param deltaTime 時間刻み
     */
    private void equalizeDensity(double deltaTime) {

        if (bodies.isEmpty()) return;

        double total = 0;

        // 正密度合計
        for (MagicalBody body : bodies) {
            total += body.getState().getRhoPositive();
        }

        double average = total / bodies.size();

        // 各体の正密度を平均値へ近づける
        for (MagicalBody body : bodies) {
            double current = body.getState().getRhoPositive();
            double diff = average - current;
            double change = diff * WorldConstants.DELTA * deltaTime;

            body.getState().addPositive(change);
        }
    }
}