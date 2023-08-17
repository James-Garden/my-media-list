<script setup lang='ts'>

import type { Ref } from 'vue';
import { ref, watch } from 'vue';
import { mediaTypes } from '@/entities/media';
import ValidatedInput from '@/components/forms/ValidatedInput.vue';
import type ValidationError from '@/entities/validation-error';
import ValidatedRadio from '@/components/forms/ValidatedRadio.vue';
import { isBlank, mapBackendToFrontendErrors } from '@/utils/validation-helper';
import { useMediaStore } from '@/stores/media';
import { useRouter } from 'vue-router';

const mediaType = ref();
const mediaTitle = ref('');
const mediaDescription = ref('');
const mediaImageUrl = ref('');
const mediaEpisodes = ref(0);
const formErrors: Ref<ValidationError[]> = ref([]);

const mediaStore = useMediaStore();
const router = useRouter();

const validationErrorMap = new Map<string, string>([
  ["type", "mediaType"],
  ["title", "mediaTitle"],
  ["description", "mediaDescription"],
  ["imageUrl", "mediaImageUrl"],
  ["episodes", "mediaEpisodes"]
]);

watch(mediaType, (newValue) => {
  formErrors.value = formErrors.value.filter((error) => error.field !== "mediaType");
  if (isBlank(newValue)) {
    formErrors.value.push({field: "mediaType", error: "Select the media type"});
  }
});

watch(mediaTitle, (newValue) => {
  formErrors.value = formErrors.value.filter((error) => error.field !== "mediaTitle");
  if (isBlank(newValue)) {
    formErrors.value.push({field: "mediaTitle", error: "Enter the title"});
  }
});

watch(mediaDescription, (newValue) => {
  formErrors.value = formErrors.value.filter((error) => error.field !== "mediaDescription");
  if (isBlank(newValue)) {
    formErrors.value.push({field: "mediaDescription", error: "Enter the description"});
  }
});

watch(mediaImageUrl, (newValue) => {
  formErrors.value = formErrors.value.filter((error) => error.field !== "mediaImageUrl");
  if (isBlank(newValue)) {
    formErrors.value.push({field: "mediaImageUrl", error: "Enter the image URL"});
  }
});

async function saveMedia() {
  const mediaOrValidationResult = await mediaStore.createMedia({
    type: mediaType.value,
    title: mediaTitle.value,
    description: mediaDescription.value,
    imageUrl: mediaImageUrl.value,
    episodes: mediaEpisodes.value
  });

  if ('id' in mediaOrValidationResult) {
    await router.push({ name: 'view-media', params: { id: mediaOrValidationResult.id } });
  } else {
    formErrors.value.push(...mapBackendToFrontendErrors(validationErrorMap, mediaOrValidationResult));
  }
}

</script>

<template>

<div class='basic-media-details'>
  <ValidatedRadio
    v-model='mediaType'
    :options='mediaTypes'
    :errors='formErrors'
    field-name='mediaType'
    label='Type'
  />

  <ValidatedInput
    v-model='mediaTitle'
    field-name='mediaTitle'
    label='Title'
    :errors='formErrors'
    type='text'
  />

  <ValidatedInput
    v-model='mediaDescription'
    field-name='mediaDescription'
    label='Description'
    :errors='formErrors'
    type='textarea'
  />

  <ValidatedInput
    v-model='mediaImageUrl'
    field-name='mediaImageUrl'
    label='Image URL'
    :errors='formErrors'
    type='url'
  />

  <ValidatedInput
    v-model='mediaEpisodes'
    field-name='mediaEpisodes'
    label='Episodes'
    type='text'
    :errors='formErrors'
  />

  <button @click='saveMedia'>Save media</button>
</div>

</template>
