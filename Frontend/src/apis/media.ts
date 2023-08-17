import type { Media, UnsavedMedia } from '@/entities/media';
import type { AxiosInstance, AxiosResponse } from 'axios';
import type ValidationError from '@/entities/validation-error';

export default class MediaApi {
  private client: AxiosInstance;

  constructor(client: AxiosInstance) {
    this.client = client;
  }

  async getMedia(id: string): Promise<Media | undefined> {
    const request = this.client.get(`/media/${id}`);
    return request
      .then(response => response.data as Media)
      .catch(() => undefined);
 }

 async createMedia(media: UnsavedMedia): Promise<Media | ValidationError[]> {
    const request = this.client.post(
      `/media`,
      media,
      { validateStatus: (status) => status === 201 || status == 422 }
    );
    return request
      .then(response => this.getMediaOrValidationResult(response));
 }

 private async getMediaOrValidationResult(response: AxiosResponse<any, any>) {
    if (response.status == 422) {
      return response.data as ValidationError[];
    }

    return response.data as Media;
 }
}
