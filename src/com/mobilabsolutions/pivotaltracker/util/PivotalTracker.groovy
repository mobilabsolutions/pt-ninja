package com.mobilabsolutions.pivotaltracker.util

@Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7')

import groovyx.net.http.RESTClient
import static groovyx.net.http.ContentType.JSON

/**
 * This class contains a bunch of methods that do things with the Pivotal Tracker API that one might find useful 
 * 
 * TODO list: 
 *    - handle success/failure properly
 *    - log useful info (have verbose on/off setting)
 * 
 * @author Vahid Gharavi (vahid@mobilabsolutions.com)
 */

class PivotalTracker {

  def apiToken;

  def base ="https://www.pivotaltracker.com/services/v5/"

  def storiesPathTemplate = "projects/%s/stories"
  def storiesLabelPathTemplate = "projects/%s/stories/%s/labels"
  def storyPathTemplate = "projects/%s/stories/%s"
  def query =["limit": 500]

  def RESTClient rest = new RESTClient(base);

  /**
   * These are default headers for every request
   */
  def headers = [
    "X-TrackerToken": apiToken,
    "Content-Type": "application/json"
  ]

  /**
   * Default constructor. Other constructors should not be allowed. How to achieve this with Groovy? FIXME
   * @param token the API token 
   */
  PivotalTracker(token) {
    this.apiToken = token;
  }

  /**
   * Returns all the stories in the specified project.
   * 
   * @param projectId the ID of the project for which all stories should be returned.
   * @return a list of stories belonging to the project
   */
  List getStories(projectId) {
    def storiesPath = String.format(storiesPathTemplate, projectId);
    def result = rest.get(path: storiesPath, headers: headers, query: query);
    return result.data
  }

  /**
   * Adds a label to all stories of a project
   * 
   * @param projectId the ID of the project
   * @param labelName the name of the label that will be added
   * 
   * @return nothing yet FIXME
   */
  def addLabelToStories(projectId, labelName) {
    List stories = getStories(projectId);
    for (story in stories) {
      addLabelToStory(projectId, story.id, labelName);
    }
  }

  /**
   * Adds a label to a story 
   *  
   * @param projectId the ID of the project the story is in 
   * @param storyId the ID of the story the label will be added to
   * @param labelName the name of the label that will be added to the story
   * 
   * @return the result of the post request which is the label object
   */
  def addLabelToStory(projectId, storyId, labelName) {
    def postBody = '"name":"' + labelName + '"';
    def storiesLabelPath = String.format(storiesLabelPathTemplate, projectId, storyId)
    def postResult = rest.post(path: storiesLabelPath, body: postBody, requestContentType: JSON, headers: headers)
    return postResult.data;
  }

  /**
   * Moves a story from one project to another project
   * 
   * @param storyId The ID of the story
   * @param sourceProjectId the ID of the source project
   * @param destProjectId the ID of the destination project
   * @return nothing at this particular moment FIXME
   */
  def moveStory(storyId, sourceProjectId, destProjectId) {
    def storiesPath = String.format(storiesPathTemplate, sourceProjectId, storyId)
    def putBody = '{"project_id":' + destProjectId + '}'
    println storiesPath
    println putBody

    rest.put(path: storiesPath, body: putBody, requestContentType: JSON, headers: headers)
  }

  /**
   * Wraps <tt>moveStory</tt> and moves all stories from one project to another
   * 
   * @param sourceProjectId the ID of the source project
   * @param destProjectId the ID of the destination project
   * @return nothing at this particular moment FIXME
   */
  def moveStories(sourceProjectId, destProjectId) {
    List stories = getStories(sourceProjectId);
    for (story in stories) {
      addLabelToStory(sourceProjectId, story.id, destProjectId);
    }
  }
}



