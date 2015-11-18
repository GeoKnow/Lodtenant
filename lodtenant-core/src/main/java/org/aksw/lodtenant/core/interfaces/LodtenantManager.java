package org.aksw.lodtenant.core.interfaces;

/**
 * - Manage lifecycle of workflows
 *   - Essentially CRUD on Workflow objects
 *
 * - Mangage lifecyle of workflow executions
 *   - Start, Abort, Pause, Resume, CleanUp
 *
 *
 * @author raven
 *
 */
public interface LodtenantManager {
    WorkflowManager register(String workflowSpec);




//    Set<String> getWorkflowAliases(String workflowId);
//    void addTags(String workflowId, Collection<String> aliases);
//    void removeAliases(String workflowId, Collection<String> aliases);
//    void removeWorkflow(String workflowId);

}
