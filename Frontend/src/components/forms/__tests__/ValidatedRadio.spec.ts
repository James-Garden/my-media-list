import { describe, expect, test } from 'vitest';
import type ValidationError from '@/entities/validation-error';
import { mount } from '@vue/test-utils';
import ValidatedRadio from '../ValidatedRadio.vue';

describe('Validated radio tests', () => {

  test.skip('renders properly without errors', () => {
    const options = {
      "value1": "Value 1 display text",
      "value2": "Value 2 display text"
    }
    const label = 'Some label text';
    const errors: ValidationError[] = [];
    const inputValue = "value1";
    const wrapper = mount(ValidatedRadio, {
      props: {
        options: options,
        fieldName: 'test-field',
        label: label,
        errors: errors,
        modelValue: inputValue
      }
    });

    const inputElementWrappers = wrapper.findAll('input');
    const labelElementWrappers = wrapper.findAll('label');
    expect(inputElementWrappers).toHaveLength(Object.keys(options).length);

    Object.keys(options)
      .forEach(optionKey => {
        const inputElementWrapper = inputElementWrappers
          .find(inputElementWrapper => inputElementWrapper.element.value === optionKey);
        expect(inputElementWrapper).toBeDefined();

        const inputElementId = inputElementWrapper?.element.id;
        //@ts-ignore
        const optionValue = options[optionKey];
        const labelWrapper = labelElementWrappers.find(labelElementWrapper => labelElementWrapper.element.htmlFor===inputElementId);
        expect(labelWrapper?.element.innerHTML).toEqual(optionValue);
      })
  });

});
