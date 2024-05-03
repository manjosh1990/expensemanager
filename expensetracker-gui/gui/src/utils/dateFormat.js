import moment from "moment";

export const dateFormat = (date) => {
  return date && moment(date).format("DD/MM/YYYY");
};
