import { afterEach, beforeEach, describe, expect, test } from 'vitest';
import MediaApi from '@/apis/media';
import type Media from '@/entities/media';
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
      mediaType: 'FILM'
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
})
