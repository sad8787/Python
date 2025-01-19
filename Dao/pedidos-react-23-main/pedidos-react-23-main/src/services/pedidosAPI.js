import axios from "axios";

const cliente =  axios.create({
  baseURL: "http://localhost:8080/api/",
  timeout: 5000,
  headers: {
    "Content-type": "application/json"
  }
});



export default cliente;
