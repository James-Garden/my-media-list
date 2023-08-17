<script setup lang='ts'>

import type ValidationError from '@/entities/validation-error';
import { findErrorByField } from '@/entities/validation-error';
import { computed } from 'vue';
import { generateElementId } from '@/utils/element-id-generator';
import { getCommonErrorMessage } from '@/utils/validation-helper';

interface Props {
  fieldName: string,
  errors: ValidationError[],
  modelValue: any,
  type: string,
  label: string
}

const props = defineProps<Props>();
const emit = defineEmits(['update:modelValue']);

const isTextarea = computed(() => props.type === 'textarea')
const elementId = computed(() => generateElementId(props.fieldName));
const activeError = computed(() => findErrorByField(props.fieldName, props.errors));
const displayedError = computed(() => {
  if (!activeError.value) {
    return activeError;
  }

  return getCommonErrorMessage(activeError.value, props.label).message;
});

function updateModelValue(event: Event) {
  emit('update:modelValue', (event.target as HTMLInputElement).value);
}

</script>

<template>

<div class='form-field-wrapper'>
  <p v-if='activeError' class='field-error'>{{ displayedError }}</p>
  <label :for='elementId' class='text-input-label'>{{ props.label }}</label>
  <input :id='elementId' :type='props.type' :value='modelValue' @input='updateModelValue' class='text-input' v-if='!isTextarea'>

  <textarea :id='elementId' :value='modelValue' @input='updateModelValue' class='text-input' v-if='isTextarea'></textarea>
</div>

</template>

<style scoped>

.text-input {
  width: 100%;
}

</style>
