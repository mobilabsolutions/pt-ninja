package com.mobilabsolutions.pivotaltracker.util.example

import com.mobilabsolutions.pivotaltracker.util.PivotalTracker

/**
 * This class demonstrates example use cases of the Pivotal Tracker util class
 * 
 * @author Vahid Gharavi (vahid@mobilabsolutions.com
 *
 */
class PtExamples {
  static void main(String[] args) {
    def token = ""; // fill in
    def pt = new PivotalTracker(token);

    // apply label to all stories in a project
    def projectId = ""; //fill in
    def label = ""; // fill in
    pt.addLabelToStories(projectId, )

    // move all stories from one project to another
    def sourceProjectId = ""; // fill in
    def destProjectId = ""; // fill in
    pt.moveStories(sourceProjectId, destProjectId);
  }
}
