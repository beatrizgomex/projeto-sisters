// K6/scripts/usuarias_todas.js
import { check, sleep } from 'k6';
import http from 'k6/http';
import { Counter, Trend } from 'k6/metrics';

// métricas customizadas
const durByStatus = new Trend('dur_by_status', true);
const failures = new Counter('failures');

export const options = {
  discardResponseBodies: true,
  thresholds: {
    'checks{type:status200}': ['rate>0.95'],          // ≥ 95% com HTTP 200
    'http_req_duration{expected_response:true}': ['p(95)<800'], // p95 < 800 ms
    'http_req_failed': ['rate<0.01'],                // falhas < 1%
  },
};

export default function () {
  // __ENV.K6_URL deve ser tipo: http://host.docker.internal:8080
  const url = `${__ENV.K6_URL}/api/usuarias/todas`;

  const res = http.get(url, {
    timeout: '10s',
    tags: { endpoint: 'usuarias_todas' },
  });

  durByStatus.add(res.timings.duration, { status: String(res.status) });

  const ok = check(
    res,
    { 'status é 200': (r) => r.status === 200 },
    { type: 'status200' } // tag pros thresholds
  );

  if (!ok) {
    failures.add(1);
    console.error(
      `FAIL status=${res.status} dur=${res.timings.duration}ms`
    );
  }

  sleep(0.1);
}
