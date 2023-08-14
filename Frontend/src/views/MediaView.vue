<script setup lang='ts'>

import { useRoute, useRouter } from 'vue-router';
import { ref } from 'vue';
import { useMediaStore } from '@/stores/media';
import type Media from '@/entities/media';

const route = useRoute();
const router = useRouter();
const mediaStore = useMediaStore();
const mediaId = ref(route.params.id);
const media = ref(await loadMedia());

async function loadMedia(): Promise<Media> {
  const media = await mediaStore.getMedia(mediaId.value as string);
  if (media === undefined) {
    await router.replace("/not-found")
      .catch(() => console.error("Failed to redirect"));
  }
  return media;
}

</script>

<template>

<div class='view-media--left-column'>
  <div class='view-media--cover-image-wrapper'>
    <img
      :src="media.imageUrl"
      alt='https://via.placeholder.com/350x450.png?text=NOT%20FOUND'
      class='view-media--cover-image'
    />
  </div>
</div>

<div class='view-media--right-column'>
  <div class='view-media--title-wrapper'>
    <h1 class='view-media--title'>{{ media.title }}</h1>
  </div>
  <p>{{ media.description }}</p>
</div>

</template>

<style scoped>

.view-media--left-column {
  flex-basis: 350px;
}

.view-media--cover-image-wrapper > img {
  border-radius: 5px;
}

.view-media--cover-image {
  max-width: 350px;
  max-height: 400px;
}

.view-media--right-column {
  flex-grow: 1;
}

.view-media--title-wrapper {
  border-bottom: solid 1px #f8f8f8;
  padding-right: 400px;
  width: min-content;
  font-weight: bolder;
  font-size: 3em;
  line-height: 0;
  max-width: 100%;
}

</style>
