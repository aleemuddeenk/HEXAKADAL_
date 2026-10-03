import React from 'react';

export default function KpiCard({ title, value, subtitle, icon: Icon, trend, color = 'cyan' }) {
  const colorStyles = {
    cyan: 'from-cyan-500/10 to-blue-500/5 border-cyan-500/20 text-cyan-400',
    emerald: 'from-emerald-500/10 to-teal-500/5 border-emerald-500/20 text-emerald-400',
    amber: 'from-amber-500/10 to-yellow-500/5 border-amber-500/20 text-amber-400',
    rose: 'from-rose-500/10 to-red-500/5 border-rose-500/20 text-rose-400',
    blue: 'from-blue-500/10 to-indigo-500/5 border-blue-500/20 text-blue-400'
  };

  return (
    <div className={`bg-gradient-to-br ${colorStyles[color]} border rounded-xl p-5 shadow-lg relative overflow-hidden backdrop-blur`}>
      <div className="flex items-center justify-between mb-2">
        <span className="text-xs font-semibold text-slate-400 uppercase tracking-wider">{title}</span>
        {Icon && (
          <div className="p-2 rounded-lg bg-slate-900/60 border border-slate-700/50">
            <Icon className="w-5 h-5" />
          </div>
        )}
      </div>

      <div className="text-2xl lg:text-3xl font-bold font-mono text-white mb-1 tracking-tight">
        {value}
      </div>

      <div className="flex items-center justify-between text-xs text-slate-400">
        <span>{subtitle}</span>
        {trend && (
          <span className={`font-mono font-semibold ${trend.startsWith('+') ? 'text-emerald-400' : 'text-rose-400'}`}>
            {trend}
          </span>
        )}
      </div>
    </div>
  );
}
