import type ValidationError from '@/entities/validation-error';

export function isBlank(text: undefined | string) {
  if (text === undefined) {
    return true;
  }

  return !text;
}

export function mapBackendToFrontendErrors(errorNameMap: Map<string, string>, errors: ValidationError[]): ValidationError[] {
  return errors.map(validationError => ({
    field: errorNameMap.get(validationError.field) ?? validationError.field,
    error: validationError.error
  }));
}

interface HumanErrorMessage {
  isCommonErrorMessage: boolean,
  message: string
}

export function getCommonErrorMessage(errorCode: string | undefined, fieldName: string): HumanErrorMessage {
  if (errorCode === 'required') {
    return { isCommonErrorMessage: true, message: `${fieldName} is required` }
  }

  return { isCommonErrorMessage: false, message: `${fieldName} is invalid` }
}
