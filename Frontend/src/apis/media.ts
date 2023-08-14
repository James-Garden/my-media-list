import type Media from '@/entities/media';
import type { AxiosInstance } from 'axios';

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
}
