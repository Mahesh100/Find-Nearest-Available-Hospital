import axios from 'axios';

const API_URL = 'http://localhost:8081/api/hospitals';

export const getAllHospitals = (params) =>
  axios.get(`${API_URL}/getAllHospitals`, { params });

export const createHospital = (hospitalData) =>
  axios.post(API_URL, hospitalData);

export const updateHospital = (id, hospitalData) =>
  axios.put(`${API_URL}/${id}`, hospitalData);

export const deleteHospital = (id) =>
  axios.delete(`${API_URL}/${id}`); 