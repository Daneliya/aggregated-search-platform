import axios from "axios";

const instance = axios.create({
  baseURL: "http://lcoalhost:8102/api",
  timeout: 10000,
  headers: { "X-Custom-Header": "foobar" },
});
export default instance;
