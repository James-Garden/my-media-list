import { afterEach, beforeEach, describe, expect, test } from 'vitest';
import MediaApi from '@/apis/media';
import type { Media } from '@/entities/media';
import AxiosMockAdapter from 'axios-mock-adapter';
import { getClient } from '@/utils/api-client-provider';

describe('Media API tests', () => {
  let mediaApi: MediaApi;
  let axiosMock: AxiosMockAdapter;

  beforeEach(() => {
    const mockedClient = getClient();
    axiosMock = new AxiosMockAdapter(mockedClient);
    mediaApi = new MediaApi(mockedClient);
  });

  afterEach(() => {
    axiosMock.reset();
  })

  test('Request a valid mediaId', async () => {
    const mediaId: string = "some-uuid";
    const expectedMedia: Media = {
      id: mediaId,
      title: 'Some media title',
      type: 'FILM',
      description: 'some desc',
      imageUrl: 'some-url',
      episodes: 1
    };

    axiosMock.onGet(`/media/${mediaId}`).reply(200, expectedMedia);

    const actualMedia = await mediaApi.getMedia(mediaId);

    expect(actualMedia).toStrictEqual(expectedMedia);
  });

  test('Request an invalid mediaId', async () => {
    const mediaId: string = "some-uuid";

    axiosMock.onGet(`/media/${mediaId}`).reply(404);

    const actualMedia = await mediaApi.getMedia(mediaId);

    expect(actualMedia).toBeUndefined();
  });

  test('Create a valid Media', async () => {
    const sentMedia: Media = {
      id: 'unused',
      title: 'Some new media title',
      type: 'SERIES',
      description: 'some desc',
      imageUrl: 'some-image-url',
      episodes: 25
    }
    const returnedMedia = structuredClone(sentMedia);
    returnedMedia.id = 'some generated UUID';

    axiosMock.onPost('/media').reply(201, returnedMedia);

    const actualMedia = await mediaApi.createMedia(sentMedia);

    expect(actualMedia).toStrictEqual(returnedMedia);
  });

  test('Create an invalid Media', async () => {
    const sentMedia: Media = {
      id: 'unused',
      title: 'Some new media title',
      type: 'SERIES',
      description: 'some desc',
      imageUrl: 'some-image-url',
      episodes: 25
    }
    const validationErrors = [
      {
        field: 'title',
        error: 'some validation error'
      }
    ];

    axiosMock.onPost('/media').reply(422, validationErrors);

    const returnedValidationErrors = await mediaApi.createMedia(sentMedia);

    expect(returnedValidationErrors).toStrictEqual(validationErrors);
  });
});
