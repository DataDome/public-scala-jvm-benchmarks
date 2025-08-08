#!/bin/bash
# Interactive selection of JVM. Will set the JVM_IDENTIFIER environment variable based on the user's choice.

select_jvm() {
  echo ""
  echo "Select a JVM:"
  echo "  1) OpenJDK HotSpot VM"
  echo "  2) GraalVM CE"
  echo "  3) Oracle GraalVM (formerly GraalVM EE)"
  echo "  4) Azul Prime VM (formerly Azul Zing VM)"
  echo ""

  while :; do
    read -r INPUT_KEY
    case $INPUT_KEY in
    1)
      export JVM_IDENTIFIER="$OPENJDK_HOTSPOT_VM_IDENTIFIER"
      break
      ;;
    2)
      export JVM_IDENTIFIER="$GRAAL_VM_CE_IDENTIFIER"
      break
      ;;
    3)
      export JVM_IDENTIFIER="$GRAAL_VM_EE_IDENTIFIER"
      break
      ;;
    4)
      export JVM_IDENTIFIER="$AZUL_PRIME_VM_IDENTIFIER"
      break
      ;;
    *)
      echo "Sorry, I don't understand. Please try again!"
      ;;
    esac
  done
}

echo ""
echo "+------------+"
echo "| Select JVM |"
echo "+------------+"
echo "The JDK version is automatically detected based on the JDK distribution found at the preconfigured 'JAVA_HOME' path."
echo "This assumes that the 'JAVA_HOME' variable has already been specified in the benchmark configuration (i.e., the ./settings/config.properties). Otherwise, the subsequent execution will fail."
select_jvm
