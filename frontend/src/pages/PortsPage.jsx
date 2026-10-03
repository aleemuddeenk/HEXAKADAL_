import React, { useState, useEffect } from 'react';
import { fetchPorts } from '../services/api';
import { Anchor, ShieldCheck, AlertTriangle, Search, Navigation } from 'lucide-react';

export default function PortsPage() {
  const [ports, setPorts] = useState([]);
  const [selectedVesselDraft, setSelectedVesselDraft] = useState(16.5);
  const [search, setSearch] = useState('');

  useEffect(() => {
    fetchPorts().then(setPorts).catch(console.error);
  }, []);

  const filteredPorts = ports.filter(p => 
    p.portName.toLowerCase().includes(search.toLowerCase()) || 
    p.country.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="space-y-6">
      {/* Draft Clearance Interactive Simulator Header */}
      <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 mb-1">
            <Anchor className="w-6 h-6 text-blue-400" />
            <h2 className="text-xl font-bold font-mono text-white">Port Draft Database &amp; Compatibility Engine</h2>
          </div>
          <p className="text-xs text-slate-400">
            Automated Draft Clearance Check (DCCM) against berth depth limits (Values in Meters)
          </p>
        </div>

        <div className="flex items-center gap-3 bg-slate-950 px-4 py-2 rounded-xl border border-slate-800 text-xs font-mono">
          <span className="text-slate-400">Target Vessel Draft:</span>
          <input
            type="number"
            step="0.5"
            value={selectedVesselDraft}
            onChange={(e) => setSelectedVesselDraft(Number(e.target.value))}
            className="w-20 bg-slate-900 border border-slate-700 rounded px-2 py-1 text-cyan-300 font-bold focus:outline-none"
          />
          <span className="text-slate-500">meters</span>
        </div>
      </div>

      {/* Search Input */}
      <div className="relative">
        <Search className="w-5 h-5 text-slate-500 absolute left-3.5 top-3" />
        <input
          type="text"
          placeholder="Filter ports by name or country (e.g. Paradip, India, Australia)..."
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="w-full bg-slate-900/80 border border-slate-800 rounded-xl pl-11 pr-4 py-2.5 text-sm text-slate-200 placeholder-slate-500 focus:outline-none focus:border-cyan-500"
        />
      </div>

      {/* Ports Grid Cards */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {filteredPorts.map((port, idx) => {
          const depthMargin = port.maxDraft - selectedVesselDraft;
          const isSafe = depthMargin >= 0.5;

          return (
            <div key={idx} className="bg-slate-900/80 border border-slate-800 rounded-xl p-5 space-y-3 relative overflow-hidden">
              <div className="flex items-start justify-between">
                <div>
                  <span className="text-[10px] font-mono uppercase tracking-wider text-cyan-400 bg-cyan-500/10 px-2 py-0.5 rounded border border-cyan-500/20">
                    {port.country} ({port.locode})
                  </span>
                  <h3 className="text-base font-bold font-mono text-white mt-1.5">{port.portName}</h3>
                </div>
                <div className="text-right">
                  <span className="text-xs text-slate-400 block font-mono">Max Berth Depth</span>
                  <span className="text-xl font-bold font-mono text-cyan-400">{port.maxDraft} m</span>
                </div>
              </div>

              <div className="pt-2 border-t border-slate-800/80 flex items-center justify-between text-xs font-mono">
                <span className="text-slate-400">Margin vs {selectedVesselDraft}m draft:</span>
                <span className={depthMargin >= 0 ? 'text-emerald-400 font-bold' : 'text-rose-400 font-bold'}>
                  {depthMargin >= 0 ? `+${depthMargin.toFixed(2)} m` : `${depthMargin.toFixed(2)} m`}
                </span>
              </div>

              <div>
                {isSafe ? (
                  <div className="flex items-center gap-1.5 text-xs text-emerald-400 font-mono font-bold bg-emerald-500/10 px-3 py-1.5 rounded-lg border border-emerald-500/20">
                    <ShieldCheck className="w-4 h-4 shrink-0" />
                    <span>SAFE DRAFT CLEARANCE</span>
                  </div>
                ) : (
                  <div className="flex items-center gap-1.5 text-xs text-rose-400 font-mono font-bold bg-rose-500/10 px-3 py-1.5 rounded-lg border border-rose-500/20">
                    <AlertTriangle className="w-4 h-4 shrink-0" />
                    <span>DRAFT VIOLATION / GROUNDING RISK</span>
                  </div>
                )}
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
