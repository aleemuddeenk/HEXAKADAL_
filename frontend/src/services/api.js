import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_URL || '/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000,
});

export const fetchDashboard = async () => {
  const response = await api.get('/dashboard');
  return response.data;
};

export const postForecast = async (requestData) => {
  const response = await api.post('/forecast', requestData);
  return response.data;
};

export const fetchHistoricalRates = async () => {
  const response = await api.get('/forecast/history');
  return response.data;
};

export const postOptimization = async (requestData) => {
  const response = await api.post('/optimization', requestData);
  return response.data;
};

export const fetchPorts = async () => {
  const response = await api.get('/ports');
  return response.data;
};

export const fetchMarketFleet = async () => {
  const response = await api.get('/market/fleet');
  return response.data;
};

export const fetchMarketCongestion = async () => {
  const response = await api.get('/market/congestion');
  return response.data;
};

export const fetchWeather = async (portName = 'Paradip Port', vesselDraft = 16.5) => {
  const response = await api.get(`/weather?portName=${encodeURIComponent(portName)}&vesselDraft=${vesselDraft}`);
  return response.data;
};

export const fetchAlerts = async () => {
  const response = await api.get('/alerts');
  return response.data;
};

export default api;
