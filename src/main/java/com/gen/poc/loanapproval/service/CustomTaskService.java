package com.gen.poc.loanapproval.service;

import org.camunda.bpm.engine.task.*;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomTaskService implements org.camunda.bpm.engine.TaskService {

    @Autowired
    private org.camunda.bpm.engine.TaskService taskService;




    public Task getTask(String taskId) {
        return  taskService.createTaskQuery().taskId(taskId).active().singleResult();
    }


    /**
     * @return
     */
    @Override
    public Task newTask() {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Task newTask(String s) {
        return null;
    }

    /**
     * @param task
     */
    @Override
    public void saveTask(Task task) {

    }

    /**
     * @param s
     */
    @Override
    public void deleteTask(String s) {

    }

    /**
     * @param collection
     */
    @Override
    public void deleteTasks(Collection<String> collection) {

    }

    /**
     * @param s
     * @param b
     */
    @Override
    public void deleteTask(String s, boolean b) {

    }

    /**
     * @param collection
     * @param b
     */
    @Override
    public void deleteTasks(Collection<String> collection, boolean b) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void deleteTask(String s, String s1) {

    }

    /**
     * @param collection
     * @param s
     */
    @Override
    public void deleteTasks(Collection<String> collection, String s) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void claim(String s, String s1) {

    }

    /**
     * @param s
     */
    @Override
    public void complete(String s) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void delegateTask(String s, String s1) {

    }

    /**
     * @param s
     */
    @Override
    public void resolveTask(String s) {

    }

    /**
     * @param s
     * @param map
     */
    @Override
    public void resolveTask(String s, Map<String, Object> map) {

    }

    /**
     * @param taskId
     * @param map
     */
    @Override
    public void complete(String taskId, Map<String, Object> map) {

        taskService.complete(taskId, map);
    }
    /**
     * @param s
     * @param map
     * @param b
     * @return
     */
    @Override
    public VariableMap completeWithVariablesInReturn(String s, Map<String, Object> map, boolean b) {
        return null;
    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void setAssignee(String s, String s1) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void setOwner(String s, String s1) {

    }

    /**
     * @param s
     * @return
     */
    @Override
    public List<IdentityLink> getIdentityLinksForTask(String s) {
        return List.of();
    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void addCandidateUser(String s, String s1) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void addCandidateGroup(String s, String s1) {

    }

    /**
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void addUserIdentityLink(String s, String s1, String s2) {

    }

    /**
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void addGroupIdentityLink(String s, String s1, String s2) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void deleteCandidateUser(String s, String s1) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void deleteCandidateGroup(String s, String s1) {

    }

    /**
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void deleteUserIdentityLink(String s, String s1, String s2) {

    }

    /**
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void deleteGroupIdentityLink(String s, String s1, String s2) {

    }

    /**
     * @param s
     * @param i
     */
    @Override
    public void setPriority(String s, int i) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void setName(String s, String s1) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void setDescription(String s, String s1) {

    }

    /**
     * @param s
     * @param date
     */
    @Override
    public void setDueDate(String s, Date date) {

    }

    /**
     * @param s
     * @param date
     */
    @Override
    public void setFollowUpDate(String s, Date date) {

    }

    /**
     * @return
     */
    @Override
    public TaskQuery createTaskQuery() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public NativeTaskQuery createNativeTaskQuery() {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param o
     */
    @Override
    public void setVariable(String s, String s1, Object o) {

    }

    /**
     * @param s
     * @param map
     */
    @Override
    public void setVariables(String s, Map<String, ?> map) {

    }

    /**
     * @param s
     * @param s1
     * @param o
     */
    @Override
    public void setVariableLocal(String s, String s1, Object o) {

    }

    /**
     * @param s
     * @param map
     */
    @Override
    public void setVariablesLocal(String s, Map<String, ?> map) {

    }

    /**
     * @param s
     * @param s1
     * @return
     */
    @Override
    public Object getVariable(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param <T>
     * @return
     */
    @Override
    public <T extends TypedValue> T getVariableTyped(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param b
     * @param <T>
     * @return
     */
    @Override
    public <T extends TypedValue> T getVariableTyped(String s, String s1, boolean b) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @return
     */
    @Override
    public Object getVariableLocal(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param <T>
     * @return
     */
    @Override
    public <T extends TypedValue> T getVariableLocalTyped(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param b
     * @param <T>
     * @return
     */
    @Override
    public <T extends TypedValue> T getVariableLocalTyped(String s, String s1, boolean b) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Map<String, Object> getVariables(String s) {
        return Map.of();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public VariableMap getVariablesTyped(String s) {
        return null;
    }

    /**
     * @param s
     * @param b
     * @return
     */
    @Override
    public VariableMap getVariablesTyped(String s, boolean b) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public Map<String, Object> getVariablesLocal(String s) {
        return Map.of();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public VariableMap getVariablesLocalTyped(String s) {
        return null;
    }

    /**
     * @param s
     * @param b
     * @return
     */
    @Override
    public VariableMap getVariablesLocalTyped(String s, boolean b) {
        return null;
    }

    /**
     * @param s
     * @param collection
     * @return
     */
    @Override
    public Map<String, Object> getVariables(String s, Collection<String> collection) {
        return Map.of();
    }

    /**
     * @param s
     * @param collection
     * @param b
     * @return
     */
    @Override
    public VariableMap getVariablesTyped(String s, Collection<String> collection, boolean b) {
        return null;
    }

    /**
     * @param s
     * @param collection
     * @return
     */
    @Override
    public Map<String, Object> getVariablesLocal(String s, Collection<String> collection) {
        return Map.of();
    }

    /**
     * @param s
     * @param collection
     * @param b
     * @return
     */
    @Override
    public VariableMap getVariablesLocalTyped(String s, Collection<String> collection, boolean b) {
        return null;
    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void removeVariable(String s, String s1) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void removeVariableLocal(String s, String s1) {

    }

    /**
     * @param s
     * @param collection
     */
    @Override
    public void removeVariables(String s, Collection<String> collection) {

    }

    /**
     * @param s
     * @param collection
     */
    @Override
    public void removeVariablesLocal(String s, Collection<String> collection) {

    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @deprecated
     */
    @Override
    public void addComment(String s, String s1, String s2) {

    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @return
     */
    @Override
    public Comment createComment(String s, String s1, String s2) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public List<Comment> getTaskComments(String s) {
        return List.of();
    }

    /**
     * @param s
     * @param s1
     * @return
     */
    @Override
    public Comment getTaskComment(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @deprecated
     */
    @Override
    public List<Event> getTaskEvents(String s) {
        return List.of();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public List<Comment> getProcessInstanceComments(String s) {
        return List.of();
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     * @param inputStream
     * @return
     */
    @Override
    public Attachment createAttachment(String s, String s1, String s2, String s3, String s4, InputStream inputStream) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     * @param s5
     * @return
     */
    @Override
    public Attachment createAttachment(String s, String s1, String s2, String s3, String s4, String s5) {
        return null;
    }

    /**
     * @param attachment
     */
    @Override
    public void saveAttachment(Attachment attachment) {

    }

    /**
     * @param s
     * @return
     */
    @Override
    public Attachment getAttachment(String s) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @return
     */
    @Override
    public Attachment getTaskAttachment(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public InputStream getAttachmentContent(String s) {
        return null;
    }

    /**
     * @param s
     * @param s1
     * @return
     */
    @Override
    public InputStream getTaskAttachmentContent(String s, String s1) {
        return null;
    }

    /**
     * @param s
     * @return
     */
    @Override
    public List<Attachment> getTaskAttachments(String s) {
        return List.of();
    }

    /**
     * @param s
     * @return
     */
    @Override
    public List<Attachment> getProcessInstanceAttachments(String s) {
        return List.of();
    }

    /**
     * @param s
     */
    @Override
    public void deleteAttachment(String s) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void deleteTaskAttachment(String s, String s1) {

    }

    /**
     * @param s
     * @return
     */
    @Override
    public List<Task> getSubTasks(String s) {
        return List.of();
    }

    /**
     * @return
     */
    @Override
    public TaskReport createTaskReport() {
        return null;
    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void handleBpmnError(String s, String s1) {

    }

    /**
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void handleBpmnError(String s, String s1, String s2) {

    }

    /**
     * @param s
     * @param s1
     * @param s2
     * @param map
     */
    @Override
    public void handleBpmnError(String s, String s1, String s2, Map<String, Object> map) {

    }

    /**
     * @param s
     * @param s1
     */
    @Override
    public void handleEscalation(String s, String s1) {

    }

    /**
     * @param s
     * @param s1
     * @param map
     */
    @Override
    public void handleEscalation(String s, String s1, Map<String, Object> map) {

    }
}
