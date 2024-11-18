check_docker_daemon() {
  while ! docker info > /dev/null 2>&1; do
    sleep 2
    echo "Docker Daemon을 기다리는 중..."
  done
  echo "Docker Daemon이 준비되었습니다."
}

manage_network() {
  NETWORK_NAME="dev_network"

  echo "Docker 네트워크 상태를 확인합니다..."
  if docker network inspect "$NETWORK_NAME" > /dev/null 2>&1; then
    echo "네트워크 $NETWORK_NAME을(를) 삭제합니다..."
    docker network rm "$NETWORK_NAME"
  else
    echo "네트워크 $NETWORK_NAME이 존재하지 않습니다. 새로 생성합니다..."
  fi

  echo "네트워크 $NETWORK_NAME을(를) 생성합니다..."
  docker network create --driver bridge "$NETWORK_NAME"
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

    # Docker Desktop 탐색
    DOCKER_DESKTOP_PATHS=(
      "C:\\Program Files\\Docker\\Docker\\Docker Desktop.exe"
      "C:\\Program Files (x86)\\Docker\\Docker\\Docker Desktop.exe"
      "D:\\Program Files\\Docker\\Docker\\Docker Desktop.exe"
      "$USERPROFILE\\AppData\\Local\\Docker\\Docker Desktop.exe"
    )

    DOCKER_FOUND=false
    for PATH in "${DOCKER_DESKTOP_PATHS[@]}"; do
      if [[ -f "$PATH" ]]; then
        start "" "$PATH"
        echo "Docker Desktop 실행 후 잠시 대기 중..."
        sleep 15
        DOCKER_FOUND=true
        break
      fi
    done

    if [[ "$DOCKER_FOUND" = false ]]; then
      echo "Docker Desktop 실행 파일을 찾을 수 없습니다. Docker CLI만 설치된 경우에도 스크립트를 계속 진행합니다."
      check_docker_daemon
    fi
  else
    echo "Docker가 이미 실행 중입니다."
  fi

else
  echo "알 수 없는 운영체제입니다. 스크립트를 실행할 수 없습니다."
  exit 1
fi

manage_network

echo "Docker Compose를 실행합니다..."
docker-compose -f docker-compose-local.yml up -d
