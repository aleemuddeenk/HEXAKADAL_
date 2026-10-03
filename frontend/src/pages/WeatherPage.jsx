import React, { useState, useEffect } from 'react';
import { fetchWeather } from '../services/api';
import { CloudSun, Waves, Wind, Clock, AlertTriangle, RefreshCw } from 'lucide-react';

export default function WeatherPage() {
  const [weather, setWeather] = useState(null);
  const [portName, setPortName] = useState('Paradip Port');
  const [vesselDraft, setVesselDraft] = useState(16.5);
  const [loading, setLoading] = useState(true);

  const loadWeather = async () => {
    setLoading(true);
    try {
      const res = await fetchWeather(portName, vesselDraft);
      setWeather(res);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadWeather();
  }, [portName, vesselDraft]);

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 mb-1">
            <CloudSun className="w-6 h-6 text-amber-400" />
            <h2 className="text-xl font-bold font-mono text-white">Live Marine Telemetry &amp; Weather Engine</h2>
          </div>
          <p className="text-xs text-slate-400">
            Real-Time Ocean Swell &amp; Wave Height Telemetry via Open-Meteo Marine API
          </p>
        </div>

        <div className="flex flex-wrap items-center gap-3 bg-slate-950 px-4 py-2 rounded-xl border border-slate-800 text-xs font-mono">
          <div>
            <span className="text-slate-400 mr-2">Port:</span>
            <select
              value={portName}
              onChange={(e) => setPortName(e.target.value)}
              className="bg-transparent text-cyan-300 font-bold focus:outline-none"
            >
              <option value="Paradip Port">Paradip Port</option>
              <option value="Visakhapatnam Port">Visakhapatnam Port</option>
              <option value="Dhamra Port">Dhamra Port</option>
              <option value="Haldia Port">Haldia Port</option>
              <option value="Gopalpur Port">Gopalpur Port</option>
            </select>
          </div>

          <button 
            onClick={loadWeather}
            className="p-1.5 rounded-lg bg-slate-800 text-cyan-400 hover:bg-slate-700 transition"
          >
            <RefreshCw className={`w-4 h-4 ${loading ? 'animate-spin' : ''}`} />
          </button>
        </div>
      </div>

      {/* Main Weather Telemetry Grid */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-5">
        {/* Wave Height Card */}
        <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 space-y-3">
          <div className="flex items-center gap-2 text-slate-400 font-mono text-xs uppercase">
            <Waves className="w-5 h-5 text-cyan-400" />
            <span>Live Wave Height</span>
          </div>
          <div className="text-3xl font-bold font-mono text-cyan-300">
            {weather?.waveHeight} m
          </div>
          <p className="text-xs text-slate-400 font-mono">
            Open-Meteo Satellite Ocean Telemetry
          </p>
        </div>

        {/* Sea Status Card */}
        <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 space-y-3">
          <div className="flex items-center gap-2 text-slate-400 font-mono text-xs uppercase">
            <Wind className="w-5 h-5 text-amber-400" />
            <span>Sea State &amp; Risk Level</span>
          </div>
          <div className="text-xl font-bold font-mono text-amber-300">
            {weather?.weatherStatus}
          </div>
          <p className="text-xs text-slate-400 font-mono">
            Delay Multiplier: <strong className="text-white">{weather?.weatherMultiplier}x</strong>
          </p>
        </div>

        {/* Anchorage Queue Card */}
        <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 space-y-3">
          <div className="flex items-center gap-2 text-slate-400 font-mono text-xs uppercase">
            <Clock className="w-5 h-5 text-emerald-400" />
            <span>Total Anchorage Queue</span>
          </div>
          <div className="text-3xl font-bold font-mono text-emerald-400">
            {weather?.totalAnchorageQueueHours} <span className="text-xs text-slate-400">Hours</span>
          </div>
          <p className="text-xs text-slate-400 font-mono">
            ~{weather?.waitingVessels} Vessels in Anchorage Queue
          </p>
        </div>
      </div>

      {/* Delay Calculation Breakdown */}
      <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 space-y-4 font-mono text-xs">
        <h3 className="text-sm font-bold text-slate-200">Intelligent Delay Estimation (IDEL) Breakdown</h3>
        <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
          <div className="bg-slate-950 p-4 rounded-lg border border-slate-800">
            <span className="text-slate-500 block mb-1">Base Port Queue</span>
            <span className="text-base font-bold text-white">{weather?.baseQueueHours} Hours</span>
          </div>

          <div className="bg-slate-950 p-4 rounded-lg border border-slate-800">
            <span className="text-slate-500 block mb-1">Tidal Window Delay</span>
            <span className="text-base font-bold text-amber-400">+{weather?.tidalDelayHours} Hours</span>
          </div>

          <div className="bg-slate-950 p-4 rounded-lg border border-slate-800">
            <span className="text-slate-500 block mb-1">Weather Delay Multiplier</span>
            <span className="text-base font-bold text-cyan-400">{weather?.weatherMultiplier}x</span>
          </div>
        </div>
      </div>
    </div>
  );
}
