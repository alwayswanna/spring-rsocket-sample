<template>
    <v-app>
        <Navbar />
        <v-sheet id="a" overflow-y-auto>
            <Messages />
        </v-sheet>
    </v-app>
</template>

<script lang="ts" setup>
import Navbar from "@/views/Navbar.vue";
import Messages from "@/views/Messages.vue";
</script>
