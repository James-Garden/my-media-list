import { v4 as uuidv4 } from 'uuid';

export function generateElementId(identifier: string) {
  return identifier + '-' + uuidv4();
}
