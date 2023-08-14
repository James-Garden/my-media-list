import type { AxiosInstance } from 'axios';
import axios from 'axios';

export function getClient(): AxiosInstance {
  return axios.create({
    baseURL: 'http://localhost:8080/api/'
  })
}
