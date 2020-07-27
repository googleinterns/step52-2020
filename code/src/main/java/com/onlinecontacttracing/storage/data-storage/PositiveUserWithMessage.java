// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.onlinecontacttracing.storage;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.onlinecontacttracing.storage.CustomizableMessage;
import java.util.ArrayList;

@Entity
public class PositiveUserWithMessage {
  @Id private String userId;
  private ArrayList<String> listOfContacts = new ArrayList<String>();
  private CustomizableMessage customMessage;

    public PositiveUserWithMessage() {}

    public PositiveUserWithMessage (ArrayList<String> contacts, CustomizableMessage message) {
        listOfContacts = contacts;
        customMessage = message;
        userId = customMessage.getUserId();
    }
    
    public ArrayList<String> getContacts() {
        return listOfContacts;
    }

    public CustomizableMessage getMessage() {
        return customMessage;
    }
}
