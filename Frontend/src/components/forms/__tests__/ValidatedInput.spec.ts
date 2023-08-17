import { describe, expect, test } from 'vitest';
import ValidatedInput from '../ValidatedInput.vue';
import { mount, VueWrapper } from '@vue/test-utils';
import type ValidationError from '../../../entities/validation-error';

describe('Validated input tests', () => {

  function expectInputAndLabel(wrapper: VueWrapper<any>,
                               inputType: string,
                               inputValue: any,
                               label: string) {
    const inputElement = wrapper.find('input');

    expect(inputElement.exists()).toBe(true);
    expect(inputElement.element.type).toEqual(inputType);
    expect(inputElement.element.value).toEqual(inputValue);

    const labelElement = wrapper.find('label');

    expect(labelElement.exists()).toBe(true);
    expect(labelElement.element.innerHTML).toEqual(label);
  }

  test('renders properly without errors', () => {
    const inputValue = 'Input text';
    const label = 'Some label text';
    const inputType = 'text';
    const errors: ValidationError[] = [];
    const wrapper = mount(ValidatedInput, {
      props: {
        fieldName: 'test-field',
        label: label,
        type: inputType,
        errors: errors,
        modelValue: inputValue
      }
    });

    expectInputAndLabel(wrapper, inputType, inputValue, label);

    expect(wrapper.find('p').exists()).toBe(false);
  });

  test('renders properly with errors', () => {
    const inputValue = 'Input text';
    const label = 'Some label text';
    const inputType = 'text';
    const fieldName = 'test-field';
    const errorMessage = `${label} is invalid`;
    const errors: ValidationError[] = [{
      field: fieldName,
      error: errorMessage
    }];
    const wrapper = mount(ValidatedInput, {
      props: {
        fieldName: fieldName,
        label: label,
        type: inputType,
        errors: errors,
        modelValue: inputValue
      }
    });

    expectInputAndLabel(wrapper, inputType, inputValue, label);

    const errorText = wrapper.find('p');
    expect(errorText.exists()).toBe(true);
    expect(errorText.element.innerHTML).toEqual(`${errorMessage}`);
  });

});
