/*******************************************************************************
 Copyright (c) Microsoft Open Technologies, Inc. All Rights Reserved.
 Licensed under the MIT or Apache License; see LICENSE in the source repository
 root for authoritative license information.﻿

 **NOTE** This code was generated by a tool and will occasionally be
 overwritten. We welcome comments and issues regarding this code; they will be
 addressed in the generation tool. If you wish to submit pull requests, please
 do so for the templates in that tool.

 This code was generated by Vipr (https://github.com/microsoft/vipr) using
 the T4TemplateWriter (https://github.com/msopentech/vipr-t4templatewriter).
 ******************************************************************************/
package com.microsoft.graph.orc;

import com.microsoft.graph.*;
import com.google.common.util.concurrent.*;
import com.microsoft.services.orc.*;
import com.microsoft.services.orc.Readable;
import com.microsoft.services.orc.interfaces.*;

/**
 * The type  ExtensionPropertyFetcher.
 */
public class ExtensionPropertyFetcher extends OrcEntityFetcher<ExtensionProperty,ExtensionPropertyOperations> 
                                     implements Readable<ExtensionProperty> {

     /**
     * Instantiates a new ExtensionPropertyFetcher.
     *
     * @param urlComponent the url component
     * @param parent the parent
     */
     public ExtensionPropertyFetcher(String urlComponent, OrcExecutable parent) {
        super(urlComponent, parent, ExtensionProperty.class, ExtensionPropertyOperations.class);
    }

     /**
     * Add parameter.
     *
     * @param name the name
     * @param value the value
     * @return the fetcher
     */
    public ExtensionPropertyFetcher addParameter(String name, Object value) {
        addCustomParameter(name, value);
        return this;
    }

     /**
     * Add header.
     *
     * @param name the name
     * @param value the value
     * @return the fetcher
     */
    public ExtensionPropertyFetcher addHeader(String name, String value) {
        addCustomHeader(name, value);
        return this;
    }

        
}