import { defineStore } from 'pinia';
import { getClient } from '@/utils/api-client-provider';
import UserApi from '@/apis/user';

export const useUserStore = defineStore('user', () => {
  const userApi = new UserApi(getClient());

  async function createAccount(email: string, displayName: string, password: string) {
    return userApi.createUser({ email: email, displayName: displayName, password: password});
  }

  return { createAccount };
});
