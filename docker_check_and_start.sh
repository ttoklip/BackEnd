check_docker_daemon() {
  while ! docker info > /dev/null 2>&1; do
    sleep 2
    echo "Docker Daemon을 기다리는 중..."
  done
  echo "Docker Daemon이 준비되었습니다."
}

if [[ "$OSTYPE" == "darwin"* ]]; then
  echo "운영체제: macOS"
  if ! docker info > /dev/null 2>&1; then
    echo "Docker가 실행 중이 아닙니다. Docker를 시작합니다..."
    open --background -a Docker
    check_docker_daemon
  else
    echo "Docker가 이미 실행 중입니다."
  fi
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
  echo "운영체제: Linux"
  if ! docker info > /dev/null 2>&1; then
    echo "Docker가 실행 중이 아닙니다. Docker를 시작합니다..."
    sudo systemctl start docker
    check_docker_daemon
  else
    echo "Docker가 이미 실행 중입니다."
  fi
elif [[ "$OSTYPE" == "msys"* ]] || [[ "$OSTYPE" == "cygwin"* ]]; then
  echo "운영체제: Windows"
  if ! docker info > /dev/null 2>&1; then
    echo "Docker가 실행 중이 아닙니다. Docker를 시작합니다..."
    start "" "C:\\Program Files\\Docker\\Docker\\Docker Desktop.exe"
    sleep 15
    check_docker_daemon
  else
    echo "Docker가 이미 실행 중입니다."
  fi
else
  echo "알 수 없는 운영체제입니다. 스크립트를 실행할 수 없습니다."
  exit 1
fi

echo "Docker Compose를 실행합니다..."
docker-compose -f docker-compose-local.yml down
docker-compose -f docker-compose-local.yml up -d
