/*******************************************************************************
**NOTE** This code was generated by a tool and will occasionally be
overwritten. We welcome comments and issues regarding this code; they will be
addressed in the generation tool. If you wish to submit pull requests, please
do so for the templates in that tool.

This code was generated by Vipr (https://github.com/microsoft/vipr) using
the T4TemplateWriter (https://github.com/msopentech/vipr-t4templatewriter).

Copyright (c) Microsoft Open Technologies, Inc. All Rights Reserved.
Licensed under the Apache License 2.0; see LICENSE in the source repository
root for authoritative license information.﻿
******************************************************************************/
package com.microsoft.services.graph;

import com.microsoft.services.orc.core.ODataBaseEntity;


/**
 * The type Task Details.
*/
public class TaskDetails extends ODataBaseEntity {

    public TaskDetails(){
        setODataType("#Microsoft.Graph.TaskDetails");
    }
            
    private String notes;
     
    /**
    * Gets the notes.
    *
    * @return the String
    */
    public String getNotes() {
        return this.notes; 
    }

    /**
    * Sets the notes.
    *
    * @param value the String
    */
    public void setNotes(String value) { 
        this.notes = value; 
        valueChanged("notes", value);

    }
            
    private String completedBy;
     
    /**
    * Gets the completed By.
    *
    * @return the String
    */
    public String getCompletedBy() {
        return this.completedBy; 
    }

    /**
    * Sets the completed By.
    *
    * @param value the String
    */
    public void setCompletedBy(String value) { 
        this.completedBy = value; 
        valueChanged("completedBy", value);

    }
            
    private ExternalReferenceCollection references;
     
    /**
    * Gets the references.
    *
    * @return the ExternalReferenceCollection
    */
    public ExternalReferenceCollection getReferences() {
        return this.references; 
    }

    /**
    * Sets the references.
    *
    * @param value the ExternalReferenceCollection
    */
    public void setReferences(ExternalReferenceCollection value) { 
        this.references = value; 
        valueChanged("references", value);

    }
            
    private String id;
     
    /**
    * Gets the id.
    *
    * @return the String
    */
    public String getId() {
        return this.id; 
    }

    /**
    * Sets the id.
    *
    * @param value the String
    */
    public void setId(String value) { 
        this.id = value; 
        valueChanged("id", value);

    }
            
    private String version;
     
    /**
    * Gets the version.
    *
    * @return the String
    */
    public String getVersion() {
        return this.version; 
    }

    /**
    * Sets the version.
    *
    * @param value the String
    */
    public void setVersion(String value) { 
        this.version = value; 
        valueChanged("version", value);

    }
}
