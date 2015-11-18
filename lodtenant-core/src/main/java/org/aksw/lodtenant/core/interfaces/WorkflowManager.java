package org.aksw.lodtenant.core.interfaces;

import java.util.Map;

public interface WorkflowManager {
    WorkflowInstance instantiate(Map<String, Object> parameters);
}
