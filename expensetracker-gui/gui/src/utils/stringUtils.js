export const capitalizeFirstLetter = (string) => {
  let newString = string.toLowerCase();
  return newString.charAt(0).toUpperCase() + newString.slice(1);
};
