import type { AxiosInstance } from 'axios';
import type { CreateUserRequest } from '@/entities/user';
import type ValidationError from '@/entities/validation-error';

export default class UserApi {
  private client: AxiosInstance;

  constructor(client: AxiosInstance) {
    this.client = client;
  }

  async createUser(data: CreateUserRequest): Promise<string | ValidationError[]> {
    const response = await this.client.post(
      "/user",
      data,
      { validateStatus: (status) => status === 201 || status == 422 }
    );
    return response.data as string | ValidationError[];
  }
}
