import { defineStore } from 'pinia';
import { ref } from 'vue';
import { getClient } from '@/utils/api-client-provider';
import type Media, { UnsavedMedia } from '@/entities/media';
import MediaApi from '@/apis/media';

export const useMediaStore = defineStore('media', () => {
  const fetchedMedia = ref(new Map<string, Media>);
  const mediaApi = new MediaApi(getClient());

  async function getMedia(mediaId: string) {
    if (fetchedMedia.value.has(mediaId)) {
      return fetchedMedia.value.get(mediaId);
    }

    const media = await mediaApi.getMedia(mediaId);
    if (media !== undefined) {
      fetchedMedia.value.set(mediaId, media);
    }
    return media;
  }

  async function createMedia(media: UnsavedMedia) {
    return mediaApi.createMedia(media);
  }

  return { getMedia, createMedia };
});
