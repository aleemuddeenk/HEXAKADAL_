import React, { useState, useEffect } from 'react';
import { fetchAlerts } from '../services/api';
import { Bell, AlertTriangle, CheckCircle2, Info, ShieldAlert, Filter } from 'lucide-react';

export default function AlertsPage() {
  const [alerts, setAlerts] = useState([]);
  const [severityFilter, setSeverityFilter] = useState('ALL');

  useEffect(() => {
    fetchAlerts().then(setAlerts).catch(console.error);
  }, []);

  const filteredAlerts = alerts.filter(a => 
    severityFilter === 'ALL' || a.severity === severityFilter
  );

  const getSeverityBadge = (severity) => {
    switch (severity) {
      case 'CRITICAL':
        return 'bg-rose-500/20 text-rose-300 border-rose-500/40';
      case 'WARNING':
        return 'bg-amber-500/20 text-amber-300 border-amber-500/40';
      case 'SUCCESS':
        return 'bg-emerald-500/20 text-emerald-300 border-emerald-500/40';
      default:
        return 'bg-cyan-500/20 text-cyan-300 border-cyan-500/40';
    }
  };

  const getSeverityIcon = (severity) => {
    switch (severity) {
      case 'CRITICAL': return ShieldAlert;
      case 'WARNING': return AlertTriangle;
      case 'SUCCESS': return CheckCircle2;
      default: return Info;
    }
  };

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="bg-slate-900/80 border border-slate-800 rounded-xl p-6 flex flex-col md:flex-row md:items-center justify-between gap-4">
        <div>
          <div className="flex items-center gap-2 mb-1">
            <Bell className="w-6 h-6 text-cyan-400" />
            <h2 className="text-xl font-bold font-mono text-white">Interactive Operational Alert Center</h2>
          </div>
          <p className="text-xs text-slate-400">
            Real-Time Notifications &amp; System Decision Warnings
          </p>
        </div>

        {/* Filter */}
        <div className="flex items-center gap-2 bg-slate-950 p-1 rounded-xl border border-slate-800 text-xs font-mono">
          <Filter className="w-4 h-4 text-slate-400 ml-2" />
          {['ALL', 'CRITICAL', 'WARNING', 'INFO', 'SUCCESS'].map((sev) => (
            <button
              key={sev}
              onClick={() => setSeverityFilter(sev)}
              className={`px-3 py-1.5 rounded-lg font-semibold transition-all ${
                severityFilter === sev ? 'bg-cyan-500/20 text-cyan-300 border border-cyan-500/30' : 'text-slate-400'
              }`}
            >
              {sev}
            </button>
          ))}
        </div>
      </div>

      {/* Alerts List */}
      <div className="space-y-4">
        {filteredAlerts.map((alert) => {
          const Icon = getSeverityIcon(alert.severity);
          return (
            <div 
              key={alert.id} 
              className="bg-slate-900/80 border border-slate-800 rounded-xl p-5 flex items-start gap-4 hover:border-slate-700 transition"
            >
              <div className={`p-3 rounded-xl border ${getSeverityBadge(alert.severity)} shrink-0`}>
                <Icon className="w-6 h-6" />
              </div>

              <div className="flex-1 min-w-0">
                <div className="flex flex-wrap items-center justify-between gap-2 mb-1">
                  <div className="flex items-center gap-2">
                    <span className="text-xs font-mono font-bold text-slate-400">{alert.id}</span>
                    <h3 className="text-base font-bold font-mono text-white">{alert.title}</h3>
                  </div>
                  <span className="text-[11px] font-mono text-slate-500">{alert.timestamp}</span>
                </div>

                <p className="text-xs text-slate-300 font-mono leading-relaxed">{alert.description}</p>

                <div className="flex items-center gap-2 mt-3">
                  <span className={`text-[10px] font-mono font-bold px-2 py-0.5 rounded border ${getSeverityBadge(alert.severity)}`}>
                    {alert.severity}
                  </span>
                  <span className="text-[10px] font-mono text-cyan-400 bg-cyan-500/10 px-2 py-0.5 rounded border border-cyan-500/20">
                    CATEGORY: {alert.category}
                  </span>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
