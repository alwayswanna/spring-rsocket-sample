<template>
    <v-main>
        <v-container>
            <v-col align-self="stretch" cols="auto">
                <v-btn icon="mdi-plus" size="x-large" @click="showForm()"></v-btn>
            </v-col>
        </v-container>
        <v-container>
                <v-row no-gutters>
                        <v-col v-for="msg in messages"
                               cols="12"
                               sm="6"
                        >
                            <v-container>
                                <v-card width="600" prepend-icon="mdi-message-text">
                                    <template v-slot:title>Message ID: {{msg.id}}</template>
                                    <v-card-text>
                                        <p>User save message with params:</p> <br>
                                        firstName: {{msg.firstName}} <br>
                                        lastName: {{msg.lastName}} <br>
                                        age: {{msg.age}} <br>
                                        birthDate: {{msg.birthDate}} <br>
                                    </v-card-text>

                                    <v-col cols="auto">
                                        <v-tooltip text="Remove message"/>
                                        <v-btn icon="mdi-cancel" size="large" @click="deleteData(msg)"></v-btn>
                                    </v-col>
                                </v-card>
                            </v-container>
                        </v-col>
                </v-row>
        </v-container>

        <v-dialog
            v-model="dialog"
            width="612"
        >
            <v-card>
                <v-card-title>
                    <span class="text-h5">Select file with messages</span>
                </v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col>
                                <v-file-input ref="file" label="Choose .xlsx file" @change="selectFile()"></v-file-input>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                        color="blue-darken-1"
                        variant="text"
                        @click="dialog = false"
                    >
                        Close
                    </v-btn>
                    <v-btn
                        color="blue-darken-1"
                        variant="text"
                        @click="sendFile()"
                    >
                        Send
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-main>
</template>

<script lang="ts">

import ApiUtils from '@/utils/ApiUtils'
import DataUtils from '@/utils/DataUtils'
import {defineComponent} from "vue";

    export default defineComponent({
        name: "all-messages component",

        data(){
          return {
              messages: [],
              dialog: false,
              file: new File([], ""),
          }
        },

        methods:{
            async loadData(){
                try{
                    let response = await ApiUtils.loadMessages();
                    await DataUtils.addDataToArray(response, this.messages);
                }catch (e) {
                    console.debug("Error while load data: ", e)
                }
            },

            async deleteData(it){
                try{
                    await ApiUtils.deleteMessage(it.id.toString());
                    this.messages = this.messages.filter(value => value !== it);
                }catch (e) {
                    console.debug("Error while delete message: ", e);
                }
            },

            showForm(){
                this.dialog = true;
            },

            selectFile(){
                this.file = this.$refs.file.files[0];
            },

            async sendFile(){
                try{
                    this.dialog = false;
                    let response = await ApiUtils.uploadFile(this.file);
                    await DataUtils.addDataToArray(response, this.messages);
                }catch (e){
                    console.debug("Error while upload file with messages");
                }
            }
        },

        beforeMount() {
            this.loadData();
        }
    })

</script>