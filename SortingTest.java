import java.io.*;
import java.util.*;

public class SortingTest {
	public static void main(String args[]) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			boolean isRandom = false;    // 입력받은 배열이 난수인가 아닌가?
			int[] value;    // 입력 받을 숫자들의 배열
			String nums = br.readLine();    // 첫 줄을 입력 받음
			if (nums.length()!=0 && nums.charAt(0) == 'r') {
				// 난수일 경우
				isRandom = true;    // 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);    // 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);    // 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);    // 최대값

				Random rand = new Random();    // 난수 인스턴스를 생성한다.

				value = new int[numsize];    // 배열을 생성한다.
				for (int i = 0; i < value.length; i++)    // 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			} else {
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];    // 배열을 생성한다.
				for (int i = 0; i < value.length; i++)    // 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true) {
				int[] newvalue = (int[]) value.clone();    // 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0)) {
					case 'B':    // Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':    // Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':    // Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':    // Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':    // Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':    // Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;    // 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");

				}
				if (isRandom) {
					// 난수일 경우 수행시간을 출력한다.
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append((System.currentTimeMillis() - t) + " ms");
					String str = stringBuilder.toString();
					System.out.println(str);
				} else {
					for (int i = 0; i < newvalue.length; i++) {
						System.out.println(newvalue[i]);
					}
				}

			}
		} catch (IOException e) {

			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static void swap(int[] array, int idx1, int idx2) {
		int tmp = array[idx1];
		array[idx1] = array[idx2];
		array[idx2] = tmp;
	}

	private static int[] DoBubbleSort(int[] value) {
		boolean swapped;
		for (int last = value.length - 1; last >= 2; last--) {
			swapped = false;
			for (int i = 0; i <= last - 1; i++) {
				if (value[i] > value[i + 1]) {
					swap(value, i, i + 1);
					swapped = true;
				}
			}
			if (!swapped) {
				break;
			}
		}
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value) {
		for (int i = 1; i <= value.length - 1; i++) {
			int loc = i - 1;
			int newitem = value[i];
			while (loc >= 0 && newitem < value[loc]) {
				value[loc + 1] = value[loc];
				loc--;
			}
			value[loc + 1] = newitem;
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value) {
		if (value.length >= 2) {
			for (int i = 1; i < value.length; i++) {
				int c = i;
				do {
					int root = (c - 1) / 2;
					if (value[root] < value[c]) {
						swap(value, root, c);
					}
					c = root;
				} while (c != 0);
			}
		}

		for (int i = value.length - 1; i >= 1; i--) {
			swap(value, 0, i);
			int root = 0;
			int child;
			do {
				child = 2 * root + 1;
				if (child < i - 1 && value[child] < value[child + 1]) {
					child++;
				}
				if (child < i && value[root] < value[child]) {
					swap(value, root, child);
				}
				root = child;
			} while (child < i);
		}
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value) {
		int b[] = new int[value.length];
		merge_sort(b, value, 0, value.length - 1);
		return value;
	}

	private static void merge_sort(int[] b, int[] value, int begin, int end) {
		if (begin < end) {
			int mid = (begin + end) / 2;
			merge_sort(b, value, begin, mid);
			merge_sort(b, value, mid + 1, end);
			merge(begin, mid, end, b, value);
		}
	}

	private static void merge(int begin, int mid, int end, int[] buf, int[] value) {
		int t = 0;
		int i = begin;
		int j = mid + 1;
		while (i <= mid && j <= end) {
			if (value[i] <= value[j]) {
				buf[t++] = value[i++];
			} else {
				buf[t++] = value[j++];
			}
		}
		while (i <= mid) {
			buf[t++] = value[i++];
		}
		while (j <= end) {
			buf[t++] = value[j++];
		}
		i = begin;
		t = 0;
		while (i <= end) {
			value[i++] = buf[t++];
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value) {
		qsort(0, value.length - 1, value);
		return (value);
	}

	private static void qsort(int p, int r, int[] value) {
		if (r - p >= 1) {
			int q = partition(p, r, value);
			qsort(p, q - 1, value);
			qsort(q + 1, r, value);
		}
	}

	private static int partition(int p, int r, int[] value) {
		int x = value[r];
		int i = p - 1;
		for (int j = p; j <= r - 1; j++) {
			if (value[j] <= x) {
				i++;
				swap(value, i, j);
			}
		}
		swap(value, i + 1, r);
		return i + 1;
	}

	private static int[] DoRadixSort(int[] value) {
		int c = 0;
		for (int i = 0; i < value.length; i++) {
			if (value[i] < 0) {
				c++;
			}
		}
		if (c == 0) {
			sort(value);
			return (value);

		}
		int[] value1 = new int[c];
		int[] value2 = new int[value.length - c];
		int k = 0, g = 0;
		for (int i = 0; i < value.length; i++) {
			if (value[i] < 0) {
				value1[k++] = -1 * value[i];
			} else {
				value2[g++] = value[i];
			}
		}
		sort(value1);
		sort(value2);

		int l = 0;
		int[] va=new int[value.length];
		for (int i = value1.length - 1; i >= 0; i--) {
			va[l++] = -1*value1[i];
		}
		for (int i = 0; i < value2.length ; i++) {
			va[l++] = value2[i];
		}
		for(int i=0;i<va.length;i++){
			value[i]=va[i];
		}

		return (value);

	}

	private static void sort(int value[]) {
		int[] cnt1 = new int[10], start1 = new int[10];
		int[] b1 = new int[value.length];
		int max = -1;
		for (int i = 0; i < value.length; i++) {
			if (value[i] > max) {
				max = value[i];
			}
		}
		int numdigit1 = (int) Math.log10(max) + 1;
		for (int digit1 = 1; digit1 <= numdigit1; digit1++) {
			Arrays.fill(cnt1, 0);
			for (int i = 0; i < value.length; i++) {
				cnt1[(int) (value[i] / Math.pow(10, digit1 - 1)) % 10]++;
			}
			start1[0] = 0;
			for (int d = 1; d <= 9; d++) {
				start1[d] = start1[d - 1] + cnt1[d - 1];
			}
			for (int i = 0; i < value.length; i++) {
				b1[start1[(int)(value[i] / Math.pow(10, digit1 - 1)) % 10]++] = value[i];
			}
			for(int i=0;i<value.length;i++){
				value[i]=b1[i];
			}

		}
	}
}