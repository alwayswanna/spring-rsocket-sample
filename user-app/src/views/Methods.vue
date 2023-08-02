<template>
  <v-main>
      <v-container>
          <v-expansion-panels>
              <v-expansion-panel title="Update message">
                  <v-expansion-panel-text>
                      <v-text-field v-model="updateId" label="ID" variant="underlined"></v-text-field>
                      <v-text-field v-model="firstName" label="Firstname" variant="underlined"></v-text-field>
                      <v-text-field v-model="lastName" label="Lastname" variant="underlined"></v-text-field>
                      <v-text-field v-model="age" label="Age" variant="underlined"></v-text-field>
                      <v-text-field v-model="birthDate" type="date" label="Birth date" variant="underlined"></v-text-field>
                      <v-btn @click="updateMessage">Update message</v-btn>
                  </v-expansion-panel-text>
              </v-expansion-panel>
              <v-expansion-panel title="Create message">
                  <v-expansion-panel-text>
                      <v-text-field v-model="firstName" clearable="true" label="Firstname" variant="underlined"></v-text-field>
                      <v-text-field v-model="lastName" clearable="true"  label="Lastname" variant="underlined"></v-text-field>
                      <v-text-field v-model="age" clearable="true" label="Age" variant="underlined"></v-text-field>
                      <v-text-field v-model="birthDate" type="date" label="Birth date" variant="underlined"></v-text-field>
                      <v-btn @click="saveMessage">Create message</v-btn>
                  </v-expansion-panel-text>
              </v-expansion-panel>
              <v-expansion-panel title="Create messages">
                  <v-expansion-panel-text>
                      <v-btn icon="mdi-plus" size="large" @click="addMore"></v-btn>
                      <div v-for="(message, index) in messages" :key="index">
                          <br>
                          <v-text-field v-model="message.firstName" label="Firstname" variant="underlined"></v-text-field>
                          <v-text-field v-model="message.lastName"  label="Lastname" variant="underlined"></v-text-field>
                          <v-text-field v-model="message.age" label="Age" variant="underlined"></v-text-field>
                          <v-text-field v-model="message.birthDate" type="date" label="Birth date" variant="underlined"></v-text-field>
                      </div>
                      <v-btn @click="saveMessages">Create messages</v-btn>
                  </v-expansion-panel-text>
              </v-expansion-panel>
              <v-expansion-panel title="Delete message">
                  <v-expansion-panel-text>
                      <v-text-field v-model="updateId" label="ID" variant="underlined"></v-text-field>
                      <v-btn @click="deleteMessage">Delete message</v-btn>
                  </v-expansion-panel-text>
              </v-expansion-panel>
          </v-expansion-panels>
      </v-container>
  </v-main>
</template>

<script lang="ts">
import ApiUtils from "@/utils/ApiUtils";
import {defineComponent} from "vue";

export default defineComponent({
      name: "api-methods",
      data() {
        return {
            updateId: null,
            firstName: null,
            lastName: null,
            age: null,
            birthDate: null,
            messages: []
        }
      },
      methods: {
          /* api methods */
          async saveMessage(){
            try{
                await ApiUtils.createMessage(this.firstName, this.lastName, this.age, this.birthDate);
                this.setDefault();
            }catch (e) {
                this.setDefault();
                console.debug("Error while save new message")
            }
          },
          async deleteMessage(){
              try{
                  await ApiUtils.deleteMessage(this.updateId);
                  this.setDefault();
              }catch(e){
                  this.setDefault();
                  console.debug("Error while delete message, with id=", this.updateId, e)
              }
          },
          async updateMessage(){
              try{
                  await ApiUtils.updateMessage(this.updateId, this.firstName, this.lastName, this.age, this.birthDate);
                  this.setDefault();
              }catch (e) {
                  this.setDefault();
                  console.debug("Error while update message, with id=", this.updateId, e)
              }
          },
          async saveMessages(){
              try{
                  await ApiUtils.createMessages(this.messages);
                  this.messages = [];
              }catch (e) {
                  this.messages = [];
                  console.debug("Error while save new messages: ", e)
              }
          },

          /* utility methods */
          addMore() {
              this.messages.push({
                  firstName: null,
                  lastName: null,
                  age: null,
                  birthDate: null
              });
          },
          setDefault(){
              this.updateId = null;
              this.firstName = null;
              this.lastName = null;
              this.age = null;
              this.birthDate = null;
          }
      },
})
</script>