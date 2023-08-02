<template>
    <v-app-bar :elevation="2" color="black">

        <v-app-bar-title class="title" @click="navigateToApi('/')">
            <v-btn @click="navigateToApi('')">RSocket Sample</v-btn>
        </v-app-bar-title>

        <v-btn class="ma-2" color="purple-darken-2" @click="navigateToApi('/api')">
            API
            <v-icon icon="mdi-dialpad"></v-icon>
        </v-btn>

    </v-app-bar>
</template>

<script lang="ts">
    export default {
        name: 'Api',
        methods: {
            navigateToApi(key){
                this.$router.push(key)
            }
        }
    }
</script>