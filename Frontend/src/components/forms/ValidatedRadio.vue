<script setup lang='ts'>

import type ValidationError from '@/entities/validation-error';
import { findErrorByField } from '@/entities/validation-error';

import { generateElementId } from '@/utils/element-id-generator';
import { computed } from 'vue';
import { getCommonErrorMessage } from '@/utils/validation-helper';

interface Props {
  options: Object,
  modelValue: any,
  errors: ValidationError[],
  fieldName: string,
  label: string
}

const props = defineProps<Props>();
const emit = defineEmits(['update:modelValue']);
const activeError = computed(() => findErrorByField(props.fieldName, props.errors));
const displayedError = computed(() => {
  if (!activeError.value) {
    return activeError;
  }

  return getCommonErrorMessage(activeError.value, props.label).message;
});
const valueIdMap = computed(() => {
  const valueIdMap = new Map();
  Object.keys(props.options)
      .forEach(optionValue => valueIdMap.set(optionValue, generateElementId(optionValue)));
  return valueIdMap;
});

function updateModelValue(event: Event) {
  emit('update:modelValue', (event.target as HTMLInputElement).value);
}

</script>

<template>

<div>
  <div class='form-field-wrapper'>
    <strong>{{ label }}</strong>
    <p v-if='activeError' class='field-error'>{{ displayedError }}</p>
  </div>
  <div v-for='(displayName, value) in options' :key='value'>
    <input type='radio'
           :id='generateElementId(value)'
           :value='value'
           :checked='value === modelValue'
           @input='updateModelValue'
    >
    <label :for='valueIdMap.get(value)' :ref='valueIdMap.get(value)'>{{ displayName }}</label>
  </div>
</div>

</template>
