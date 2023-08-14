<script setup lang="ts">
import { RouterView } from 'vue-router';
</script>

<template>
  <header>
    <!-- Header will go here -->
  </header>

  <main>
    <div class='container'>
      <Suspense>
        <RouterView/>
      </Suspense>
    </div>
  </main>

  <footer>
    <!-- Footer will go here -->
  </footer>
</template>

<style scoped>

.container {
  display: flex;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
}

</style>
