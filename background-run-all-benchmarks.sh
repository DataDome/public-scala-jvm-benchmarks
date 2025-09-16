# Timestamp the start of the process
date >benchmarks.log

nohup bash <<EOF &
echo 'no' | ./run-all-benchmarks.sh >benchmarks.log 2>&1
EOF

# Timestamp the end of the process
date >benchmarks.log