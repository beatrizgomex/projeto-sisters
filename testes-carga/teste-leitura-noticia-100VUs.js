import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  scenarios: {
    get_noticias_100vus: {
      executor: 'constant-vus',
      vus: 100,
      duration: '60s',
      gracefulStop: '5s',
    },
  },

  thresholds: {
    http_req_duration: ['p(95)<500'],
    http_req_failed: ['rate<0.01'],
  },
};

export default function () {
  const res = http.get('http://localhost:8080/api/v1/noticias');

  check(res, {
    'status é 200': (r) => r.status === 200,
    'latência < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}
