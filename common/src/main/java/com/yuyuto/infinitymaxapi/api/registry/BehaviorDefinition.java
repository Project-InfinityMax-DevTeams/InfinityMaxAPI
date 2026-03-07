package com.yuyuto.infinitymaxapi.api.registry;

import com.yuyuto.infinitymaxapi.api.logic.Logic;

/**
 * @param trigger Logic Execute Trigger
 * @param logic   Execute Logic
 */
public record BehaviorDefinition(String trigger, Logic logic) {

}
