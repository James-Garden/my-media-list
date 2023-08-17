export default interface ValidationError {
  field: string,
  error: string
}

export function findErrorByField(field: string, errors: ValidationError[]): string | undefined {
  return errors.find(error => error.field === field)?.error;
}
