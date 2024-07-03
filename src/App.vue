<template>
  <div id="app">
    <AuthContainer v-if="showAuthContainer" />
    <router-view></router-view>
  </div>
</template>

<script>
import AuthContainer from './components/AuthContainer.vue'
import { computed } from 'vue';
import { useRoute } from 'vue-router';

export default {
  name: 'App',
  components: {
    AuthContainer
  },
  setup() {
    const route = useRoute();
    const showAuthContainer = computed(() => {
      return route.name !== 'MyAuth';
    });
    return {
      showAuthContainer
    };
  }
}
</script>
