package com.yuyuto.infinitymaxapi.api.registry;

import com.yuyuto.infinitymaxapi.api.behavior.Phase;
import com.yuyuto.infinitymaxapi.api.logic.Logic;

import java.util.Map;

/**
 * @param trigger Logic Execute Trigger
 * @param logic   Execute Logic
 */
public record BehaviorDefinition(Phase trigger, Logic logic, Map<String, Object> meta) {
}
