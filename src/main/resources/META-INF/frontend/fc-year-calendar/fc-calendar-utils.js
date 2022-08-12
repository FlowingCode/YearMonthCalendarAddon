
export function formatDate(date) {
  var offset = date.getTimezoneOffset() * 60000;
  return new Date(date.getTime() - offset).toISOString();
}
