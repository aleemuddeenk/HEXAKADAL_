import React, { useState, useEffect } from 'react';
import { fetchMarketFleet, fetchMarketCongestion } from '../services/api';
import { BarChart3, Ship, Globe, Clock, Layers } from 'lucide-react';

export default function MarketPage() {
  const [fleet, setFleet] = useState([]);
  const [congestion, setCongestion] = useState([]);
  const [activeTab, setActiveTab] = useState('fleet');

  useEffect(() => {
    fetchMarketFleet().then(setFleet).catch(console.error);
    fetchMarketCongestion().then(setCongestion).catch(console.error);
  }, []);

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 mb-1">
            <BarChart3 className="w-6 h-6 text-cyan-400" />
            <h2 className="text-xl font-bold font-mono text-white">Global Maritime Market &amp; Fleet Intelligence</h2>
          </div>
          <p className="text-xs text-slate-400">
            UNCTAD Port Turnaround Data &amp; Bulk Carrier Vessel Specifications
          </p>
        </div>

        <div className="flex items-center gap-2 bg-slate-950 p-1 rounded-xl border border-slate-800 text-xs font-mono">
          <button
            onClick={() => setActiveTab('fleet')}
            className={`px-3 py-1.5 rounded-lg font-semibold transition-all ${
              activeTab === 'fleet' ? 'bg-cyan-500/20 text-cyan-300 border border-cyan-500/30' : 'text-slate-400'
            }`}
          >
            Vessel Fleet ({fleet.length})
          </button>
          <button
            onClick={() => setActiveTab('congestion')}
            className={`px-3 py-1.5 rounded-lg font-semibold transition-all ${
              activeTab === 'congestion' ? 'bg-cyan-500/20 text-cyan-300 border border-cyan-500/30' : 'text-slate-400'
            }`}
          >
            UNCTAD Congestion Benchmark ({congestion.length})
          </button>
        </div>
      </div>

      {activeTab === 'fleet' ? (
        <div className="bg-slate-900/80 border border-slate-800 rounded-xl overflow-hidden">
          <div className="p-4 border-b border-slate-800 font-mono text-sm font-bold text-slate-200">
            Bulk Carrier &amp; Container Fleet Registry
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs font-mono">
              <thead className="bg-slate-950 text-slate-400 uppercase border-b border-slate-800">
                <tr>
                  <th className="px-4 py-3">Ship Name</th>
                  <th className="px-4 py-3">Company / Class</th>
                  <th className="px-4 py-3">Built Year</th>
                  <th className="px-4 py-3">Deadweight (DWT)</th>
                  <th className="px-4 py-3">Gross Tonnage (GT)</th>
                  <th className="px-4 py-3">Est. Draft</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-800/60 text-slate-300">
                {fleet.map((v, i) => (
                  <tr key={i} className="hover:bg-slate-800/40">
                    <td className="px-4 py-3 font-bold text-cyan-300">{v.shipName}</td>
                    <td className="px-4 py-3 text-slate-400">{v.companyName}</td>
                    <td className="px-4 py-3">{v.builtYear}</td>
                    <td className="px-4 py-3 text-emerald-400 font-bold">{v.dwt?.toLocaleString()} MT</td>
                    <td className="px-4 py-3">{v.gt?.toLocaleString()}</td>
                    <td className="px-4 py-3 text-amber-300">{v.estimatedDraft} m</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      ) : (
        <div className="bg-slate-900/80 border border-slate-800 rounded-xl overflow-hidden">
          <div className="p-4 border-b border-slate-800 font-mono text-sm font-bold text-slate-200">
            UNCTAD Port Median Waiting Times &amp; Vessel Statistics
          </div>
          <div className="overflow-x-auto">
            <table className="w-full text-left text-xs font-mono">
              <thead className="bg-slate-950 text-slate-400 uppercase border-b border-slate-800">
                <tr>
                  <th className="px-4 py-3">Country / Region</th>
                  <th className="px-4 py-3">Commercial Market</th>
                  <th className="px-4 py-3">Median In-Port Days</th>
                  <th className="px-4 py-3">Avg Cargo Capacity</th>
                  <th className="px-4 py-3">Reporting Period</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-800/60 text-slate-300">
                {congestion.slice(0, 20).map((c, i) => (
                  <tr key={i} className="hover:bg-slate-800/40">
                    <td className="px-4 py-3 font-bold text-cyan-300">{c.economyLabel}</td>
                    <td className="px-4 py-3 text-slate-400">{c.commercialMarketLabel}</td>
                    <td className="px-4 py-3 text-amber-300 font-bold">{c.medianTimeInPortDays} days</td>
                    <td className="px-4 py-3 text-emerald-400">{c.averageCargoDwt?.toLocaleString()} DWT</td>
                    <td className="px-4 py-3 text-slate-500">{c.period}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  );
}
