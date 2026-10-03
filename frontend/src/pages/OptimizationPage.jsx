import React, { useState, useEffect } from 'react';
import { postOptimization } from '../services/api';
import { Sliders, Cpu, CheckCircle2, ShieldAlert, DollarSign, RefreshCw } from 'lucide-react';

export default function OptimizationPage() {
  const [cargoQty, setCargoQty] = useState(150000);
  const [destPort, setDestPort] = useState('Paradip Port');
  const [demurrageOverride, setDemurrageOverride] = useState(30000);
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const runOpt = async () => {
    setLoading(true);
    try {
      const res = await postOptimization({
        cargoQuantity: cargoQty,
        destinationPort: destPort,
        demurrageRateOverride: demurrageOverride
      });
      setResult(res);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    runOpt();
  }, []);

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 mb-1">
            <Cpu className="w-6 h-6 text-cyan-400" />
            <h2 className="text-xl font-bold font-mono text-white">Google OR-Tools Optimization Engine</h2>
          </div>
          <p className="text-xs text-slate-400">
            Constraint Solver &amp; Financial Risk Trade-Off Minimizer (OR-Tools Java CBC)
          </p>
        </div>

        <button
          onClick={runOpt}
          disabled={loading}
          className="flex items-center gap-2 bg-gradient-to-r from-cyan-600 to-blue-600 hover:from-cyan-500 hover:to-blue-500 text-white text-xs font-mono font-bold px-5 py-2.5 rounded-xl shadow-lg shadow-cyan-500/20 transition disabled:opacity-50"
        >
          <RefreshCw className={`w-4 h-4 ${loading ? 'animate-spin' : ''}`} />
          <span>Execute OR Solver</span>
        </button>
      </div>

      {/* Inputs Grid */}
      <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 grid grid-cols-1 sm:grid-cols-3 gap-5 text-xs font-mono">
        <div>
          <label className="block text-slate-400 mb-1">Cargo Volume (Metric Tons)</label>
          <input
            type="number"
            step="5000"
            value={cargoQty}
            onChange={(e) => setCargoQty(Number(e.target.value))}
            className="w-full bg-slate-950 border border-slate-700 rounded-lg px-3 py-2 text-cyan-300 font-bold focus:outline-none focus:border-cyan-500"
          />
        </div>

        <div>
          <label className="block text-slate-400 mb-1">Destination Port</label>
          <select
            value={destPort}
            onChange={(e) => setDestPort(e.target.value)}
            className="w-full bg-slate-950 border border-slate-700 rounded-lg px-3 py-2 text-cyan-300 font-bold focus:outline-none focus:border-cyan-500"
          >
            <option value="Paradip Port">Paradip Port (17.5m)</option>
            <option value="Visakhapatnam Port">Visakhapatnam Port (16.1m)</option>
            <option value="Dhamra Port">Dhamra Port (18.0m)</option>
            <option value="Haldia Port">Haldia Port (8.5m)</option>
            <option value="Gopalpur Port">Gopalpur Port (14.5m)</option>
          </select>
        </div>

        <div>
          <label className="block text-slate-400 mb-1">Demurrage Rate Override ($/day)</label>
          <input
            type="number"
            step="1000"
            value={demurrageOverride}
            onChange={(e) => setDemurrageOverride(Number(e.target.value))}
            className="w-full bg-slate-950 border border-slate-700 rounded-lg px-3 py-2 text-cyan-300 font-bold focus:outline-none focus:border-cyan-500"
          />
        </div>
      </div>

      {/* Solver Results Card */}
      {result && (
        <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 space-y-6">
          <div className="flex flex-col sm:flex-row items-start sm:items-center justify-between gap-4 border-b border-slate-800 pb-4">
            <div>
              <span className="text-[10px] font-mono uppercase text-slate-400 block">OPTIMIZATION SOLVER RESULT</span>
              <h3 className="text-xl font-bold font-mono text-cyan-300">{result.selectedVessel}</h3>
            </div>
            <div className="flex items-center gap-2">
              <span className="bg-emerald-500/10 text-emerald-400 border border-emerald-500/30 text-xs font-mono font-bold px-3 py-1 rounded-lg">
                STATUS: {result.solutionStatus}
              </span>
              <span className="bg-slate-800 text-slate-300 border border-slate-700 text-xs font-mono px-3 py-1 rounded-lg">
                {result.solverType}
              </span>
            </div>
          </div>

          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4 font-mono text-xs">
            <div className="bg-slate-950 p-4 rounded-xl border border-slate-800">
              <span className="text-slate-400 block mb-1">Net Decision Value</span>
              <span className={`text-xl font-bold ${result.netDecisionValue > 0 ? 'text-emerald-400' : 'text-rose-400'}`}>
                ${result.netDecisionValue?.toLocaleString()}
              </span>
            </div>

            <div className="bg-slate-950 p-4 rounded-xl border border-slate-800">
              <span className="text-slate-400 block mb-1">Potential Savings</span>
              <span className="text-xl font-bold text-cyan-400">
                ${result.potentialFreightSavings?.toLocaleString()}
              </span>
            </div>

            <div className="bg-slate-950 p-4 rounded-xl border border-slate-800">
              <span className="text-slate-400 block mb-1">Demurrage Loss Risk</span>
              <span className="text-xl font-bold text-amber-400">
                ${result.demurrageCost?.toLocaleString()}
              </span>
            </div>

            <div className="bg-slate-950 p-4 rounded-xl border border-slate-800">
              <span className="text-slate-400 block mb-1">Draft Safety Margin</span>
              <span className="text-xl font-bold text-slate-200">
                {result.vesselDraft}m / {result.portMaxDraft}m
              </span>
            </div>
          </div>

          <div className="bg-slate-950 p-5 rounded-xl border border-slate-800 font-mono text-xs space-y-2">
            <div className="text-cyan-400 font-bold">Recommended Executive Action: [{result.executiveSignal}]</div>
            <p className="text-slate-300 leading-relaxed">{result.xaiReason}</p>
          </div>
        </div>
      )}
    </div>
  );
}
