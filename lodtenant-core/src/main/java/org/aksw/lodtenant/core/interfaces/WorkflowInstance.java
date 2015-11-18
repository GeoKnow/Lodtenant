package org.aksw.lodtenant.core.interfaces;

public interface WorkflowInstance {
    void start();
    void pause();
    void abort();
    String getStatus();

    /**
     * Remove this workflow instance.
     * Workflow must not be running, otherwise InvalidStateException will be raised.
     */
    void remove();
}
