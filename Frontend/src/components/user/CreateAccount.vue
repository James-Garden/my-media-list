<script setup lang='ts'>

import ValidatedInput from '@/components/forms/ValidatedInput.vue';
import type { Ref } from 'vue';
import { ref } from 'vue';
import { useUserStore } from '@/stores/user';
import type ValidationError from '@/entities/validation-error';

const userStore = useUserStore();

const displayName = ref("");
const email = ref("");
const password = ref("");
const errors: Ref<ValidationError[]> = ref([]);

async function createAccount() {
  const result = await userStore.createAccount(email.value, displayName.value, password.value);
  if (Array.isArray(result)) {
    errors.value.push(...result);
    return;
  }
  alert(`User created with ID: ${result}`);
}

</script>

<template>
  <div>
    <h1>Create account</h1>
    <ValidatedInput
      v-model='displayName'
      field-name='displayName'
      label='Display name'
      type='text'
      :errors='errors'
    />
    <ValidatedInput
      v-model='email'
      field-name='email'
      label='E-mail'
      type='email'
      :errors='errors'
    />
    <ValidatedInput
      v-model='password'
      field-name='password'
      label='Password'
      type='password'
      :errors='errors'
    />

    <button @click='createAccount'>Create account</button>
  </div>
</template>
